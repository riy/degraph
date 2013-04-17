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

/**
 * provides access to configurations and scalatest matchers useful when testing for dependencies.
 *
 * With all members of Check imported a simple use case looks like this:
 *
 *     classpath.forType("module").allow("advertising", "billing", "customer") should be (violationFree)
 *
 * Such a test checks that
 * * all slice types are cycle free.
 * * for the slicetype *module* the slices *advertising* may depend on
 * *billing* and *customer*, and *billing* may depend on *customer*.
 * * Dependencies in other directions are not allowed.
 *
 * Other slices not mentioned in the constraint definition may depend on each other or on the slices in the constraint.
 */
object Check {

    /**
     * a Configuration object containing the current classpath.
     *
     * Intended as a starting point for analyzing the application that is currently running.
     *
     * Note in a typical maven like setup it will also include the test classes as well as all libraries, which might not be desirable.
     * Manipulate the configuration to only contain the classpath elements required or use include and exclude filters for limiting the analyzed classes.
     */
    val classpath = SliceConstraintBuilder(new Configuration(
        classpath = Option(System.getProperty("java.class.path")),
        analyzer = Analyzer), "package")

    /**
     * a matcher for Configurations testing if the classes specified in the configuration
     * adhere to the dependency constraints configured in the configuration.
     */
    val violationFree = new BeMatcher[SliceConstraintBuilder] {
        def apply(constraintBuilder: SliceConstraintBuilder) = {
            val conf = constraintBuilder.configuration
            val g = conf.createGraph()

            def checkForViolations: Set[(Node, Node)] = {
                for {
                    c <- conf.constraint
                    v <- c.violations(g)
                } yield v
            }

            val violations = checkForViolations

            val matches = violations.isEmpty

            val failureMessage = "The configuration %s contains edges in cycles or edges in violation of constraints: %s".format(conf, violations)
            val negativeFailureMessage = "The configuration %s does not contain any circular dependencies nor violations of constraints".format(conf)

            new MatchResult(matches, failureMessage, negativeFailureMessage)
        }
    }

    private def sliceNode(edge: (Node, Node)) = {
        val (n1, n2) = edge
        n1.types == n2.types && n1.types.head != "Class"
    }

    private def lowerCaseFirstLetter(s: String) = s.head.toLower + s.tail
}

