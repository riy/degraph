package de.schauderhaft.degraph.java

import de.schauderhaft.degraph.graph.HierarchicGraph
import de.schauderhaft.degraph.model.Node
import java.util.{ Set => JSet }

trait JavaHierarchicGraph {
    def topNodes: JSet[Node]
    def contentsOf(group: Node): JSet[Node]
    def connectionsOf(node: Node): JSet[Node]
    def allNodes: JSet[Node]
}