package de.schauderhaft.degraph.java

import de.schauderhaft.degraph.graph.HierarchicGraph
import de.schauderhaft.degraph.model.Node
import java.util.{ Set => JSet }

/**
 * an interface for easy Java interoperation with the Degraph Graph calss / the HierarchicGraph trait.
 *
 *  This interface should be sufficient when creating some kind of visual representation of a Degraph Graph.
 */
trait JavaHierarchicGraph {

    /**
     * the root nodes of the graph, i.e. the nodes visible when all nodes are collapsed
     */
    def topNodes: JSet[Node]

    /**
     * all the nodes that are inside the node given as a parameter. If the parameter represents an empty node and empty Set is returned
     */
    def contentsOf(group: Node): JSet[Node]

    /**
     * all the nodes the argument node depends on. Only produces dependencies of the actual dependend node.
     *
     * If A contains B and B depends on C
     *
     * * connectionsOf A will be empty
     * * connectionsOf B returns Set(C)
     */
    def connectionsOf(node: Node): JSet[Node]

    /**
     * all the nodes of the graph regardless of their dependency relationship.
     */
    def allNodes: JSet[Node]
}