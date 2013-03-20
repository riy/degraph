package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.model.Node
import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult

object Check {
    val classpath = new Configuration(
        classpath = Option(System.getProperty("java.class.path")),
        analyzer = Analyzer)

    private def sliceNode(edge: (Node, Node)) = {
        val (n1, n2) = edge
        n1.types == n2.types && n1.types.head != "Class"
    }

    val violationFree = new BeMatcher[Configuration] {
        private def lowerCaseFirstLetter(s: String) = s.head.toLower + s.tail
        def apply(conf: Configuration) = {

            val g = conf.createGraph(Analyzer)
            val edgesInCycles = g.edgesInCycles.filter(sliceNode)

            val failureMessage = "The configuration " + conf + " contains the circular dependency " + edgesInCycles
            val negativeFailureMessage = "The configurations does not contain any circular dependencies."

            new MatchResult(edgesInCycles.isEmpty, failureMessage, negativeFailureMessage)
        }
    }
}

