package de.schauderhaft.degraph.analysis

/**
 * companion object providing factory methods for often needed kinds of Node instances.
 */
object Node {
    final val classType = "Class"
    final val packageType = "Package"
    def classNode(name: String) = Node(classType, name)
    def packageNode(name: String) = Node(packageType, name)
}

/**
 * represents a node in the dependency graph.
 */
case class Node(
    nodeType: String,
    name: String)
