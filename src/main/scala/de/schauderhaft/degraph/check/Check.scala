package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult

object Check {
    val classpath = new Configuration
    val cycles = new HavePropertyMatcher[Configuration, Set[Violation]] {
        def apply(conf: Configuration): HavePropertyMatchResult[Set[Violation]] = {
            new HavePropertyMatchResult(true, "dependency cycles", Set(), Set())
        }
    }
}

case class Violation()
