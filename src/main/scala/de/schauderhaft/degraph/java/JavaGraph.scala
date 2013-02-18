package de.schauderhaft.degraph.java

import java.util.HashSet
import de.schauderhaft.degraph.graph.Graph
import scala.collection.JavaConverters._
import scala.xml.XML
import de.schauderhaft.degraph.writer.Writer
import de.schauderhaft.degraph.model.Node

/**
 * a class intendent to use with  java, so it skips on all the fancy Scala stuff.
 *
 *
 *  See JavaApiTest#writeGraphMlFromGraphExampl for example usage.
 */
class JavaGraph(graph: Graph) {
    def this() = this(new Graph)

    def this(categorizer: Categorizer) = this(new Graph(categorizer.categoryOf _))

    def topNodes(): java.util.Set[Object] = graph.topNodes.asJava

    def add(node: Node): Unit = graph.add(node)

    def connectionsOf(node: Object): java.util.Set[Object] = graph.connectionsOf(node).asJava

    def connect(a: Node, b: Node): Unit = graph.connect(a, b)
    def save(fileName: String) {
        XML.save(fileName, (new Writer()).toXml(graph), "UTF-8", true, null)
    }
    def contentsOf(node: Object): java.util.Set[Object] = graph.contentsOf(node).asJava
}

