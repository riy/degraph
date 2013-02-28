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
class Graph(category: Node => Node = (x) => x,
    filter: Node => Boolean = _ => true,
    edgeFilter: ((Node, Node)) => Boolean = _ => true) {

    import Graph._

    private val internalGraph = SGraph[Node, LkDiEdge]()

    def topNodes: Set[Node] =
        for {
            n <- internalGraph.nodes.toSet
            if (n.incoming.forall(_.label != contains)) // only the nodes not contained inside another one
        } yield n.value

    private def connectedNodes(node: Node, connectionType: String): Set[Node] =
        for {
            n <- internalGraph.find(node).toSet[internalGraph.NodeT]
            e <- n.outgoing
            if (e.label == connectionType)
        } yield e._2.value

    def contentsOf(group: Node): Set[Node] = connectedNodes(group, contains)

    def connectionsOf(node: Node): Set[Node] = connectedNodes(node, references)

    def add(node: Node) = if (filter(node)) unfilteredAdd(node)

    private def unfilteredAdd(node: Node) {
        val cat = category(node)
        if (cat == node) {
            internalGraph += node
        } else {
            addNodeToSlice(node, cat)
            unfilteredAdd(cat)
        }
    }

    def connect(a: Node, b: Node) = {
        addEdge(a, b)
        add(a)
        add(b)
    }

    def allNodes: Set[Node] = internalGraph.nodes.map(_.value).toSet

    def slice(name: String) = {

        def sliceNodes = internalGraph.nodes.map(_.value).collect { case n: SimpleNode if (n.nodeType == name) => n }

        val sliceNodeFinder = new SliceNodeFinder(name, internalGraph)

        val sliceGraph = SGraph[Node, LkDiEdge]()
        sliceNodes.foreach(sliceGraph.add(_))

        //---------------
        implicit val factory = scalax.collection.edge.LkDiEdge
        val edges = internalGraph.edges
        for {
            e <- edges
            if (e.label == references)
            s1 <- sliceNodeFinder.lift(e._1.value)
            s2 <- sliceNodeFinder.lift(e._2.value)
        } sliceGraph.addLEdge(s1, s2)(references)

        sliceGraph
    }

    private def addEdge(a: Node, b: Node) {
        implicit val factory = scalax.collection.edge.LkDiEdge
        if (filter(a) && filter(b) && edgeFilter(a, b))
            internalGraph.addLEdge(a, b)(references)
    }

    private def addNodeToSlice(node: Node, cat: Node) = {
        implicit val factory = scalax.collection.edge.LkDiEdge
        internalGraph.addLEdge(cat, node)(contains)
    }

    def edgesInCycles: Set[(Node, Node)] = {
        val sliceTypes = internalGraph.nodes.flatMap(_.types)
        val edges = (for {
            st <- sliceTypes
            s <- slice(st).findCycle.toList
            e <- s.edgeIterator
        } yield (e.edge._1.value, e.edge._2.value)).toSet

        edges
    }
}

/**
 * for a graph containing the ParentAwareNodes representing the cross product of various slices types, the SliceNodeFinder finds the nodes representing a single slice type
 */
class SliceNodeFinder(slice: String, graph: SGraph[Node, LkDiEdge]) extends PartialFunction[Node, SimpleNode] {

    private def contains(pan: ParentAwareNode): Boolean =
        pan.vals.exists {
            case n: SimpleNode if (n.nodeType == slice) => true
            case _ => false
        }

    private def findIn(pan: ParentAwareNode): SimpleNode =
        pan.vals.collectFirst { case n: SimpleNode if (n.nodeType == slice) => n }.get

    private def container(n: Node) = {
        val containers = for {
            in <- graph.find(n).toSeq
            ie <- in.incoming
            if (ie.label == Graph.contains)
        } yield ie._1.value
        containers.headOption
    }

    def isDefinedAt(n: Node): Boolean = n match {
        case node: SimpleNode if (node.nodeType == slice) => true
        case pan: ParentAwareNode if (contains(pan)) => true
        case _ => container(n) match {
            case Some(c) => isDefinedAt(c)
            case _ => false
        }
    }
    def apply(n: Node): SimpleNode = n match {
        case node: SimpleNode if (node.nodeType == slice) => node
        case pan: ParentAwareNode => findIn(pan)
        case _ => apply(container(n).get)
    }
}