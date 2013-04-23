package de.schauderhaft.degraph.configuration

import de.schauderhaft.degraph.graph.SliceSource
import de.schauderhaft.degraph.model.Node
import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.edge.LkDiEdge
import de.schauderhaft.degraph.graph.Graph

/**
 * constraints the allowed dependencies of a dependency graph.
 */
trait Constraint {
    /**
     * returns a set of (Node, Node) tuple representing violations of the constraint.
     * The nodes may refer to nodes of a slice, thus representing more than a single dependency in the orginal graph.
     */
    def violations(ss: SliceSource): Iterable[(Node, Node)]
}

case class ConstraintViolation(sliceType: String, name: String, dependencies: (String, String)*) {
    override def toString = {
        val formattedDeps = dependencies
            .map(t => "%n    " + t._1 + " -> " + t._2)
            .mkString("").format()
        "[%s](%s):%s".format(sliceType, name, formattedDeps)
    }
}

/**
 * constraints the graph to be cycle free for all slice types
 */
object CycleFree extends Constraint {
    private def cyclicDependencies(sg: SGraph[Node, LkDiEdge]) = {
        def iter(sg: SGraph[Node, LkDiEdge], cyclicDependencies: Set[(Node, Node)]): Set[(Node, Node)] = {
            val newDeps = for {
                s <- sg.findCycle.toList
                e <- s.edgeIterator
            } yield (e.edge._1.value, e.edge._2.value)

            if (newDeps.isEmpty) cyclicDependencies
            else iter(sg -- newDeps.map((d: (Node, Node)) => LkDiEdge.newEdge[Node, String](d, Graph.references)), cyclicDependencies ++ newDeps)
        }

        iter(sg, Set())
    }

    def violations(ss: SliceSource) = {
        val edges = (for {
            st <- ss.slices
            if (st != "Class")
            d <- cyclicDependencies(ss.slice(st))
        } yield d).toSet

        edges
    }
}