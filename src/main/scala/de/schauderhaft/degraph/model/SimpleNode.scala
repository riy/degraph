package de.schauderhaft.degraph.model

/**
 * companion object providing factory methods for often needed kinds of Node instances.
 */
object SimpleNode {
    final val classType = "Class"
    final val packageType = "Package"
    def classNode(name: String) = SimpleNode(classType, name)
    def packageNode(name: String) = SimpleNode(packageType, name)
}

sealed trait Node

/**
 * represents a node in the dependency graph.
 */
case class SimpleNode(
    nodeType: String,
    name: String) extends Node

case class ParentAwareNode(vals: Node*) extends Node {
    def prune = if (vals.size == 1) vals.head else this
    def next = if (vals.size > 1) new ParentAwareNode(vals.tail: _*) else this
    def head = vals.head
}
