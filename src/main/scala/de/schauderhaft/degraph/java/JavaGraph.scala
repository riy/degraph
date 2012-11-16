package de.schauderhaft.degraph.java

import java.util.HashSet
import de.schauderhaft.degraph.graph.Graph
import scala.collection.JavaConverters._

/**
 * a class intendent to use with a java, so it skips on all the fancy Scala stuff.
 */
class JavaGraph {
    val graph = new Graph()

    def topNodes(): java.util.Set[Object] = graph.topNodes.asJava

    def add(node: Object): Unit = graph.add(node)

    def connectionsOf(node: Object): java.util.Set[Object] = graph.connectionsOf(node).asJava

    def connect(a: Object, b: Object): Unit = graph.connect(a, b)
}

