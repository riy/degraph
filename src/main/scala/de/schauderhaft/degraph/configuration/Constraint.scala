package de.schauderhaft.degraph.configuration

import scala.io.Source
import org.rogach.scallop.exceptions.ScallopException
import Slicer.toSlicer
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import de.schauderhaft.degraph.slicer.CombinedSlicer
import de.schauderhaft.degraph.slicer.ParallelCategorizer
import de.schauderhaft.degraph.analysis.dependencyFinder.IncludeExcludeFilter
import de.schauderhaft.degraph.slicer.InternalClassCategorizer
import de.schauderhaft.degraph.slicer.PackageCategorizer
import de.schauderhaft.degraph.slicer.MultiCategorizer.combine
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.slicer.PatternMatchingFilter
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.graph.SliceSource
import scalax.collection.edge.LkDiEdge
import scalax.collection.mutable.{ Graph => SGraph }
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

/**
 * a constraint that applies only to a certain sliceType.
 *
 */
trait SlicedConstraint extends Constraint {
    def sliceType: String

    /**
     * a predicate determining if a given node pair violates this constraint.
     *
     * Will get called with nodes from the slice type of this constraint
     */
    def isViolatedBy(n1: Node, n2: Node): Boolean

    protected def slices: IndexedSeq[Layer]
    protected def indexOf(n: Node) = n match {
        case sn: SimpleNode => slices.indexWhere(_.contains(sn.name))
        case _ => throw new IllegalStateException("Sorry, I thought this would never happen, please report a bug including the callstack")
    }

    /**
     * implemented in such a way that all dependencies of the specified slice type will be iterated and @link isViolatedBy called for each dependency
     */
    def violations(ss: SliceSource): Set[(Node, Node)] = {
        val sg = ss.slice(sliceType)
        (for {
            eT <- sg.edges.toSeq
            val e = eT.value
            if (isViolatedBy(e._1, e._2))
        } yield (e._1.value, e._2.value)).toSet
    }
}

/**
 * provides a DSLish method of creating Layer instances
 */
object Layer {
    def anyOf(es: String*) = LenientLayer(es: _*)
}

/**
 * one or more slices making up an architectural layer, i.e. a group of slices all constraint in the same way.
 */
trait Layer {
    def contains(elem: String): Boolean
}

/**
 * a layer where the elements of that layer may depend on each other
 */
case class LenientLayer(es: String*) extends Layer {
    private[this] val eSet = es.toSet
    def contains(elem: String): Boolean = eSet.contains(elem)
}

/**
 * a layer where the elements of that may NOT depend on each other
 */
case class StrictLayer(es: String*) extends Layer {
    private[this] val eSet = es.toSet
    def contains(elem: String): Boolean = eSet.contains(elem)
}

/**
 * each layer (A) mentioned in this constraint may only depend on a layer (B) when A comes before B
 *
 * Dependencies from and to slices not represented by a layer aren't constraint by this constraint
 */
case class LayeringConstraint(sliceType: String, slices: IndexedSeq[Layer]) extends SlicedConstraint {
    def isViolatedBy(n1: Node, n2: Node) = {
        val i1 = indexOf(n1)
        val i2 = indexOf(n2)
        i1 >= 0 &&
            i2 >= 0 &&
            (i1 > i2 ||
                (n1 != n2 && i1 == i2 && slices(i1).isInstanceOf[StrictLayer]))
    }

}

/**
 * each layer (A) mentioned in this constraint may only depend on a layer (B) when A comes directly before B
 */
case class DirectLayeringConstraint(sliceType: String, slices: IndexedSeq[Layer]) extends SlicedConstraint {
    def isViolatedBy(n1: Node, n2: Node) = {
        val i1 = indexOf(n1)
        val i2 = indexOf(n2)
        (i1 >= 0 &&
            i2 >= 0 &&
            (i1 > i2 ||
                i2 - i1 > 1 || (n1 != n2 && i1 == i2 && slices(i1).isInstanceOf[StrictLayer]))) ||
                (i1 < 0 && i2 > 0) ||
                (i1 >= 0 && i1 < slices.size - 1 && i2 < 0)
    }
}
