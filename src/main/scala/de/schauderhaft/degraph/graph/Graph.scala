package de.schauderhaft.degraph.graph

import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._
import scalax.collection.edge.Implicits._
import scalax.collection.edge.LkDiEdge
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.ParentAwareNode
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
            if (n.incoming.forall(_.label != contains)) // only the nodes not contained inside another one
        } yield n.value

    private def connectedNodes(node: AnyRef, connectionType: String): Set[AnyRef] =
        for {
            n <- internalGraph.find(node).toSet[internalGraph.NodeT]
            e <- n.outgoing
            if (e.label == connectionType)
        } yield e._2.value

    def contentsOf(group: AnyRef): Set[AnyRef] = connectedNodes(group, contains)

    def connectionsOf(node: AnyRef): Set[AnyRef] = connectedNodes(node, references)

    def addNode(node: Node) = add(node)

    private def add(node: AnyRef) = if (filter(node)) unfilteredAdd(node)

    private def unfilteredAdd(node: AnyRef) {
        val cat = category(node)
        if (cat == node) {
            internalGraph += node
        } else {
            addNodeToCategory(node, cat)
            unfilteredAdd(cat)
        }
    }

    def connectNodes(a: Node, b: Node) = connect(a, b)

    def connect(a: AnyRef, b: AnyRef) {
        addEdge(a, b)
        add(a)
        add(b)
    }

    def allNodes: Set[AnyRef] = internalGraph.nodes.map(_.value).toSet

    def slice(name: String) = {

        def sliceNodes = internalGraph.nodes.map(_.value).collect { case n: SimpleNode if (n.nodeType == name) => n }

        val sliceNodeFinder = new SliceNodeFinder(name, internalGraph)

        val sliceGraph = SGraph[AnyRef, LkDiEdge]()
        sliceNodes.foreach(sliceGraph.add(_))

        //---------------
        implicit val factory = scalax.collection.edge.LkDiEdge
        val edges = internalGraph.edges
        val sliceEdges = (for {
            e <- edges
            if (e.label == references)
            s1 <- sliceNodeFinder.lift(e._1.value)
            s2 <- sliceNodeFinder.lift(e._2.value)
        } sliceGraph.addLEdge(s1, s2)(references))

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

class SliceNodeFinder(slice: String, graph: SGraph[AnyRef, LkDiEdge]) extends PartialFunction[AnyRef, SimpleNode] {

    private def contains(pan: ParentAwareNode): Boolean =
        pan.vals.exists {
            case n: SimpleNode if (n.nodeType == slice) => true
            case _ => false
        }

    private def findIn(pan: ParentAwareNode): SimpleNode =
        pan.vals.collectFirst { case n: SimpleNode if (n.nodeType == slice) => n }.get

    private def container(n: AnyRef) = {
        val containers = for {
            in <- graph.find(n).toSeq
            ie <- in.incoming
            if (ie.label == Graph.contains)
        } yield ie._1.value
        containers.headOption
    }

    def isDefinedAt(n: AnyRef) = n match {
        case node: SimpleNode if (node.nodeType == slice) => true
        case pan: ParentAwareNode if (contains(pan)) => true
        case _ => container(n) match {
            case Some(c) => isDefinedAt(c)
            case _ => false
        }
    }
    def apply(n: AnyRef): SimpleNode = n match {
        case node: SimpleNode if (node.nodeType == slice) => node
        case pan: ParentAwareNode => findIn(pan)
        case _ => apply(container(n).get)
    }
}