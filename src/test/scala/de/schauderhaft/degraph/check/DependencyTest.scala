package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import Check._

@RunWith(classOf[JUnitRunner])
class DependencyTest extends FunSuite with ShouldMatchers {

    test("Degraph has no cycles") {
        classpath.including("de.schauderhaft.**") should have(noCycles)
    }

    test("some of the external libs have cycles") {
        classpath.including("org.apache.log4j.**") should not have (noCycles)
    }

    test("Degraph honors its constraints") {
        pending
        // classpath.including("de.schauderhaft.**") should not have (violations.with("someFile").expecting("X:y" -> "Z:b"))
    }
}