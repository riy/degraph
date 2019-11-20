package de.schauderhaft.degraph.java

import java.util.HashSet
import de.schauderhaft.degraph.graph.Graph
import scala.collection.JavaConverters._
import scala.xml.XML
import de.schauderhaft.degraph.writer.Writer
import de.schauderhaft.degraph.model.Node
import java.util.{ Set => JSet }

/**
 * a class intendent to use with  java, so it skips on all the fancy Scala stuff.
 *
 *
 *  See JavaApiTest#writeGraphMlFromGraphExampl for example usage.
 */
class JavaGraph(graph: Graph) extends JavaHierarchicGraph {
    def this() = this(new Graph)

    def this(categorizer: Categorizer) = this(new Graph(categorizer.categoryOf _))

    def allNodes(): JSet[Node] = graph.allNodes.asJava

    def topNodes(): JSet[Node] = graph.topNodes.asJava

    def add(node: Node): Unit = graph.add(node)

    def connectionsOf(node: Node): java.util.Set[Node] = graph.connectionsOf(node).asJava

    def connect(a: Node, b: Node): Unit = graph.connect(a, b)
    def save(fileName: String) = {
        XML.save(fileName, (new Writer()).toXml(graph), "UTF-8", true, null)
    }
    def contentsOf(node: Node): java.util.Set[Node] = graph.contentsOf(node).asJava
}

