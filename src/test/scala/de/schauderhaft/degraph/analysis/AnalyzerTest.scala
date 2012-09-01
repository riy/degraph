package de.schauderhaft.degraph.analysis

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.ShouldMatchers

import de.schauderhaft.degraph.graph.Graph

@RunWith(classOf[JUnitRunner])
class AnalyzerTest extends FunSuite with ShouldMatchers {
    private val testClassFolder = "./bin"
    private val graph = Analyzer.analyze(testClassFolder, (x) => x, _ => true)
    def stringNodes = graph.topNodes.map(_.toString)
    def nodeByString(name: String) = graph.topNodes.find(_.toString == name)

    test("Dependency from cub to superclass is found") {
        graph should connect("de.schauderhaft.degraph.examples.SubClass" -> "de.schauderhaft.degraph.examples.SuperClass")
    }

    test("Dependency from class to usage in constructor is found") {
        graph should connect("de.schauderhaft.degraph.examples.User" -> "de.schauderhaft.degraph.examples.Token")
    }

    test("Dependency from class to member class is found") {
        graph should connect("de.schauderhaft.degraph.examples.OtherUser" -> "de.schauderhaft.degraph.examples.Token")
    }

    test("Dependency from class to annotation") {
        graph should connect("de.schauderhaft.degraph.examples.UsesAnnotation" -> "org.junit.runner.RunWith")
    }

    test("Dependency from class to class used in annotation") {
        graph should connect("de.schauderhaft.degraph.examples.UsesAnnotation" -> "de.schauderhaft.degraph.examples.MyRunner")
    }

    test("No self references") {
        for (
            n <- graph.topNodes;
            n2 <- graph.connectionsOf(n)
        ) n should not be n2
    }

    private def connect(connection: (String, String)) = {
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
                if (messages.isEmpty && !graph.connectionsOf(fromNode.get).contains(toNode.get))
                    messages = "there is no connection from %s to %s in %s".format(from, to, graph) :: messages
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