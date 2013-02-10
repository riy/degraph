package de.schauderhaft.degraph.graph

import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._
import scalax.collection.edge.Implicits._
import scalax.collection.edge.LkDiEdge
import de.schauderhaft.degraph.model.Node

object Graph {
    val contains = "contains"
    val references = "references"
}

/**
 * a special graph for gathering and organizing dependencies in a hirachical fashion.
 *
 * Argument is a category which is by default the identity. A category is a function that returns an outer node for any node and the node itself if no out node is available
 */
class Graph(category: AnyRef => AnyRef = (x) => x,
    filter: AnyRef => Boolean = _ => true,
    edgeFilter: ((AnyRef, AnyRef)) => Boolean = _ => true) {

    import Graph._

    private val internalGraph = SGraph[AnyRef, LkDiEdge]()

    def topNodes: Set[AnyRef] =
        for {
            n <- internalGraph.nodes.toSet
            if (n.incoming.forall(_.label != "contains")) // only the nodes not contained inside another one
        } yield n.value

    private def connectedNodes(node: AnyRef, connectionType: String): Set[AnyRef] =
        for {
            n <- internalGraph.find(node).toSet[internalGraph.NodeT]
            e <- n.outgoing
            if (e.label == connectionType)
        } yield e._2.value

    def contentsOf(group: AnyRef): Set[AnyRef] = connectedNodes(group, contains)

    def connectionsOf(node: AnyRef): Set[AnyRef] = connectedNodes(node, references)

    def add(node: AnyRef) = if (filter(node)) unfilteredAdd(node)

    private def unfilteredAdd(node: AnyRef) {
        val cat = category(node)
        if (cat == node) {
            internalGraph += node
        } else {
            addNodeToCategory(node, cat)
            unfilteredAdd(cat)
        }
    }

    def connect(a: AnyRef, b: AnyRef) {
        addEdge(a, b)
        add(a)
        add(b)
    }

    def allNodes: Set[AnyRef] = internalGraph.nodes.map(_.value).toSet

    def slice(name: String) = {
        val sliceGraph = SGraph[AnyRef, LkDiEdge]()
        //---------------
        val nodes = internalGraph.nodes.map(_.value).collect { case n: Node => n }
        for {
            n <- nodes
            if (n.nodeType == name)
        } sliceGraph.add(n)
        //---------------
        implicit val factory = scalax.collection.edge.LkDiEdge
        val edges = internalGraph.edges
        for {
            e <- edges
            in1 <- e._1.incoming
            if (in1.label == contains)
            val s1 = in1._1
            in2 <- e._2.incoming
            if (in2.label == contains)
            val s2 = in2._1
        } sliceGraph.addLEdge(s1.value, s2.value)(references)

        sliceGraph
    }

    private def addEdge(a: AnyRef, b: AnyRef) {
        implicit val factory = scalax.collection.edge.LkDiEdge
        if (filter(a) && filter(b) && edgeFilter(a, b))
            internalGraph.addLEdge(a, b)(references)
    }

    private def addNodeToCategory(node: AnyRef, cat: AnyRef) = {
        implicit val factory = scalax.collection.edge.LkDiEdge
        internalGraph.addLEdge(cat, node)(contains)
    }

    def edgesInCycles =
        (for {
            c <- internalGraph.findCycle.toList
            e <- c.edgeIterator
        } yield (e._1.value, e._2.value)).toSet

}