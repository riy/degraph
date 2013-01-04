package de.schauderhaft.degraph.analysis

object Node {
    final val classType = "Class"
    final val packageType = "Package"
    def classNode(name: String) = Node(classType, name)
    def packageNode(name: String) = Node(packageType, name)
}

case class Node(
    nodeType: String,
    name: String)
