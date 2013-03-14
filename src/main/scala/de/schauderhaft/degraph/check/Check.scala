package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.model.Node

object Check {
    val classpath = new Configuration(
        classpath = Option(System.getProperty("java.class.path")))

    val noCycles = new HavePropertyMatcher[Configuration, Set[(Node, Node)]] {
        def apply(conf: Configuration): HavePropertyMatchResult[Set[(Node, Node)]] = {
            val g = conf.createGraph(Analyzer)
            val edgesInCycles = g.edgesInCycles.filter(sliceNode)
            new HavePropertyMatchResult(edgesInCycles.isEmpty, "dependency cycles", Set(), edgesInCycles)
        }
    }

    private def sliceNode(edge: (Node, Node)) = {
        val (n1, n2) = edge
        if (n1.types != n2.types) {
            println("strange edge: " + edge)
            false
        } else
            n1.types.head != "Class"
    }
}

