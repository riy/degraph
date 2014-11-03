package de.schauderhaft.degraph.analysis.test

import org.scalatest.FunSuite
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.Matcher
import org.scalatest.Matchers._
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.analysis._

@RunWith(classOf[JUnitRunner])
class AnalyzerTest extends FunSuite {
  private val testClassFolder = System.getProperty("java.class.path")
  println(testClassFolder)
  private val graphs = Map(
    "asm" -> asm.Analyzer.analyze(testClassFolder, x => x, _ => true))


  for ((label, graph) <- graphs) {
    def stringNodes = graph.topNodes.map(_.toString)

    def nodeByString(name: String) = graph.topNodes.find(
      x => x match {
        case n: SimpleNode => n.name == name
        case _ => false
      })

    def test(name: String)(testFun: => Unit) = super.test("%s (%s)".format(name, label))(testFun)

    test("Selftest: nodeByString works") {
      nodeByString("java.lang.String") should be(Some(classNode("java.lang.String")))
    }

    test("Test classes get found") {
      nodeByString("de.schauderhaft.degraph.examples.OtherUser") should be (Some(classNode("de.schauderhaft.degraph.examples.OtherUser")))
    }


    test("Selftest: example classes got analyzed") {
      nodeByString("de.schauderhaft.degraph.examples.SubClass") should not be (None)
    }

    test("Dependency from sub to superclass is found") {
      graph should connect("de.schauderhaft.degraph.examples.SubClass" -> "de.schauderhaft.degraph.examples.SuperClass")
    }

    test("Dependency from class to usage in constructor is found") {
      graph should connect("de.schauderhaft.degraph.examples.User" -> "de.schauderhaft.degraph.examples.Token")
    }


    test("Dependency from class to interface is found") {
      graph should connect("de.schauderhaft.degraph.examples.AnImplementation" -> "de.schauderhaft.degraph.examples.AnInterface")
    }

    test("Dependency from class to member type is found") {
      graph should connect("de.schauderhaft.degraph.examples.OtherUser" -> "de.schauderhaft.degraph.examples.Token")
    }

    test("Dependency from class to annotation") {
      graph should connect("de.schauderhaft.degraph.examples.UsesAnnotation" -> "org.junit.runner.RunWith")
    }

    test("Dependency from class to class used in annotation") {
      graph should connect("de.schauderhaft.degraph.examples.UsesAnnotation" -> "de.schauderhaft.degraph.examples.MyRunner")
    }

    test("Dependency from class to class used as an array") {
      graph should connect("de.schauderhaft.degraph.examples.DependsOnArray" -> "java.lang.String")
    }

    test("Dependency from class to class used only inside a method") {
      graph should connect("de.schauderhaft.degraph.examples.UsageInMethod" -> "java.lang.String")
      graph should connect("de.schauderhaft.degraph.examples.UsageInMethod" -> "java.lang.System")
    }


    test("Dependency from class to type parameter of superclass") {
      if (label == "depFinder") pending
      graph should connect("de.schauderhaft.degraph.examples.MyArrayList" -> "java.lang.String")
    }

    test("Dependency from class to type parameter of member") {
      if (label == "depFinder") pending
      graph should connect("de.schauderhaft.degraph.examples.ListUser" -> "java.lang.String")
    }

    test("Dependency from class to type parameter of abstract member") {
      if (label == "depFinder") pending
      graph should connect("de.schauderhaft.degraph.examples.ListUser" -> "java.lang.String")
    }

    test("Dependency from class to enum parameter in annotation") {
      if (label == "depFinder") pending
      graph should connect ("de.schauderhaft.degraph.examples.EnumInAnnotationUser" -> "java.lang.annotation.RetentionPolicy")
    }

    test("Dependency from instance to static method") {
      graph should connect ("de.schauderhaft.degraph.examples.InstanceReferenceToStaticMethod" -> "de.schauderhaft.degraph.examples.StaticMethod")
    }

    test("Dependency from class to static method") {
      graph should connect ("de.schauderhaft.degraph.examples.StaticReferenceToStaticMethod" -> "de.schauderhaft.degraph.examples.StaticMethod")
    }

    test("No self references") {
      for (
        n <- graph.topNodes;
        n2 <- graph.connectionsOf(n)
      ) n should not be n2
    }

    test("class with annotation with array of classes"){
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "java.lang.Number")
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "java.lang.Double")
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "java.lang.Float")
    }

    test("class with annotation with annotation with annotation"){
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "de.schauderhaft.degraph.examples.AnnoA")
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "de.schauderhaft.degraph.examples.AnnoB")
      graph should connect ("de.schauderhaft.degraph.examples.ClassWithAnnotationWithArrayOfClasses" -> "de.schauderhaft.degraph.examples.AnnoC")
    }


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
            !toNode.isEmpty
              && !fromNode.isEmpty
              && graph.connectionsOf(fromNode.get).contains(toNode.get),
            messages.mkString(","),
            "There is a connection from %s to %s in %s".format(from, to, graph))
        }
      }
    }
  }
}