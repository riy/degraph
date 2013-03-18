package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.OptionValues._
import de.schauderhaft.degraph.configuration.Configuration
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike

@RunWith(classOf[JUnitRunner])
class CheckTest extends FunSuite with ShouldMatchers {
    test("configuration cotains the classpath") {
        Check.classpath.classpath.value should include("log4j")
    }

    //        forType("module").allow("a", "b", any("x","y","z"), none("u","v","w",)"c", )
    //        allowDirectOnly

    test("matcher accepts violation free graph for simple layering") {
        val conf = new Configuration with MockCreate {
            val graph = new Graph()
        }
        val constraint = conf.forType("mod").allow("a", "b", "c")

        Check.violationFree(constraint).matches should be(true)
    }

    trait MockCreate {
        this: Configuration =>
        val graph: Graph
        override def createGraph(any: AnalyzerLike) = graph

    }

}

