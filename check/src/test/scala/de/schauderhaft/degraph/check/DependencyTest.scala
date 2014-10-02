package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import org.scalatest.FunSuite
import Check._

@RunWith(classOf[JUnitRunner])
class DependencyTest extends FunSuite {

  test("Degraph honors its constraints") {
    classpath.noJars.including("de.schauderhaft.**").
      withSlicing("part", "de.schauderhaft.*.(*).**").
      withSlicing("lib", "de.schauderhaft.**(Test)",
        ("main", "de.schauderhaft.*.**")).
        withSlicing("internalExternal", ("internal", "de.schauderhaft.**"), ("external", "**")) should be(violationFree)
  }

  test("Check identifies cycles in junit") {
    classpath.printTo("junitDependencyFailure.graphml").including("**.junit.**") should  be (violationFree)
  }

  test("Degraph has no cycles") {
    classpath.noJars.including("de.schauderhaft.**") should be(violationFree)
  }
}