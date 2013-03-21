package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult
import de.schauderhaft.degraph.analysis.dependencyFinder.Analyzer
import de.schauderhaft.degraph.model.Node
import org.scalatest.matchers.BeMatcher
import org.scalatest.matchers.MatchResult
import de.schauderhaft.degraph.graph.Graph
import org.scalatest.matchers.ShouldMatchers._

object Check {
    val classpath = new Configuration(
        classpath = Option(System.getProperty("java.class.path")),
        analyzer = Analyzer)

    private def sliceNode(edge: (Node, Node)) = {
        val (n1, n2) = edge
        n1.types == n2.types && n1.types.head != "Class"
    }

    private def lowerCaseFirstLetter(s: String) = s.head.toLower + s.tail

    val violationFree = new BeMatcher[Configuration] {
        def apply(conf: Configuration) = {
            val g = conf.createGraph()

            def checkForViolations = {
                if (conf.constraint.isEmpty)
                    Set()
                else {
                    val constraint = conf.constraint.head
                    val sg = g.slice(constraint.sliceType)
                    sg.edges.filter(e => constraint.isViolatedBy(e._1, e._2))
                }
            }

            def checkForCycles = {
                g.edgesInCycles.filter(sliceNode)
            }

            val violations = checkForViolations
            val cycle = checkForCycles

            val matches = violations.isEmpty & cycle.isEmpty

            val failureMessage = "The configuration %s contains edges in cycles: %s or edges in violation of constraints: %s".format(conf, cycle, violations)
            val negativeFailureMessage = "The configuration %s does not contain any circular dependencies nor violations of constraints".format(conf)

            new MatchResult(matches, failureMessage, negativeFailureMessage)

        }
    }

}

