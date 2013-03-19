package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.OptionValues._
import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.Node

@RunWith(classOf[JUnitRunner])
class CheckTest extends FunSuite with ShouldMatchers {
    val mod = "mod"

    test("configuration contains the classpath") {
        Check.classpath.classpath.value should include("log4j")
    }

    //        forType("module").allow("a", "b", any("x","y","z"), none("u","v","w",)"c", )
    //        allowDirectOnly

    private def mockConfig(conns: Traversable[(Node, Node)]) =
        new Configuration() with MockCreate {
            val graph = new Graph()
            for ((a, b) <- conns) graph.connect(a, b)
        }

    val ascending = for {
        x <- 'a' to 'c'
        y <- 'a' to 'c'
        if (x <= y)
    } yield (SimpleNode(mod, x.toString), SimpleNode(mod, y.toString))

    val descending = for {
        x <- 'a' to 'c'
        y <- 'a' to 'c'
        if (x > y)
    } yield (SimpleNode(mod, x.toString), SimpleNode(mod, y.toString))

    test("matcher accepts violation free graph for simple layering") {
        pending
        val conf = mockConfig(ascending).forType(mod).allow("a", "b", "c")
        Check.violationFree(conf).matches should be(true)
    }

    for (illegalCon <- descending)
        test("matcher detects violations for simple layering %s".format(illegalCon)) {
            pending
            val conf = mockConfig(Set(illegalCon)).forType(mod).allow("a", "b", "c")
            Check.violationFree(conf).matches should be(false)
        }

    for (illegalCon <- descending)
        test("matcher detects cycles %s".format(illegalCon)) {
            pending
            val conf = mockConfig(ascending :+ illegalCon).forType(mod).allow("a", "b", "c")
            Check.violationFree(conf).matches should be(false)
        }

    trait MockCreate {
        this: Configuration =>
        val graph: Graph
        abstract override def createGraph(any: AnalyzerLike) = graph
    }

}

