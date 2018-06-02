package de.schauderhaft.degraph.jdk8tests

import de.schauderhaft.degraph.analysis._
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import SimpleNode._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.Matchers._


@RunWith(classOf[JUnitRunner])
class AnalyzerTest extends FunSuite {

  private val testClassFolder = System.getProperty("java.class.path")

  private def isJavaVersionWithBrokenTypeAnnotations: Boolean = {
    val javaVersionString = System.getProperty("java.version")
    javaVersionString > "1.8.0_31"
  }

  private val graphs =
    if (isJavaVersionWithBrokenTypeAnnotations)
      Map()
    else
      Map(
        "asm" -> asm.Analyzer.analyze(testClassFolder, x => x, _ => true))


  for ((label, graph) <- graphs) {
    def stringNodes = graph.topNodes.map(_.toString)

    def nodeByString(name: String) = graph.topNodes.find {
      case n: SimpleNode => n.name == name
      case _ => false
    }

    def test(name: String)(testFun: => Unit) = super.test("%s (%s)".format(name, label))(testFun)

    test("Selftest: nodeByString works") {
      nodeByString("java.lang.String") should be(Some(classNode("java.lang.String")))
    }


    test("Dependency from class to type annotation on implements is found") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno1")
    }
    test("Dependency from class to type annotation on type parameter") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno2")
    }
    test("Dependency from class to type annotation on constructor is found") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno3")
    }
    test("Dependency from class to type annotation on cast is found") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno4")
    }
    test("Dependency from class to type annotation on exception is found") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno5")
    }
    test("Dependency from class to type annotation on field is found") {
      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno6")
    }
//    test("Dependency from class to type annotation on type in catch clause is found") {
//      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno7")
//    }
//    test("Dependency from class to type annotation on local variable is found") {
//      graph should connect("de.schauderhaft.degraph.jdk8tests.ClassWithTypeAnnotations" -> "de.schauderhaft.degraph.jdk8tests.TypeAnno8")
//    }


    def connect(connection: (String, String)) = {
      val (from, to) = connection
      new Matcher[Graph] {
        override def apply(graph: Graph) = {
          var messages = List[String]()
          val toNode = nodeByString(to)
          if (toNode.isEmpty)
            messages = "there is no node %s in the graph %s".format(to, graph) :: messages
          val fromNode = nodeByString(from)
          if (fromNode.isEmpty)
            messages = "there is no node %s in the graph %s".format(from, graph) :: messages
          else {
            val connections = graph.connectionsOf(fromNode.get)
            if (messages.isEmpty && !connections.contains(toNode.get))
              messages = "there is no connection from %s to %s in %s. The only connections are %s".format(from, to, graph, connections) :: messages
          }
          new MatchResult(
            toNode.nonEmpty
              && fromNode.nonEmpty
              && graph.connectionsOf(fromNode.get).contains(toNode.get),
            messages.mkString(","),
            "There is a connection from %s to %s in %s".format(from, to, graph))
        }
      }
    }
  }

  // if annotations are broken, at least verify that broken stuff doesn't completely kill the analysis
  if (isJavaVersionWithBrokenTypeAnnotations) {
    test("Analysis does not explode") {
      asm.Analyzer.analyze(testClassFolder, x => x, _ => true)
    }
  }

}