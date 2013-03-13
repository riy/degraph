package de.schauderhaft.degraph.check

import de.schauderhaft.degraph.configuration.Configuration
import org.scalatest.matchers.HavePropertyMatcher
import org.scalatest.matchers.HavePropertyMatchResult

object Check {
    val classpath = new Configuration
    val cycles = new HavePropertyMatcher[Configuration, Unit] {
        def apply(conf: Configuration): HavePropertyMatchResult[Unit] = {
            new HavePropertyMatchResult[Unit](true, "dependency cycles", (), ())
        }
    }
}