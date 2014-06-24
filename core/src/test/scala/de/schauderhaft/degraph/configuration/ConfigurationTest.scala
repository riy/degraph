package de.schauderhaft.degraph.configuration

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.AnalyzerLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.model.Node

@RunWith(classOf[JUnitRunner])
class ConfigurationTest extends FunSuite {



  test("a configuration without classpath to analyze is not valid") {
    Configuration(
      None,
      Seq(),
      Seq(),
      Map(),
      Some("output")) should not be ('valid)
  }

  test("a configuration without output to analyze is not valid") {
    Configuration(
      Some("."),
      Seq(),
      Seq(),
      Map(),
      None) should not be ('valid)
  }

  test("a complete configuration is valid") {
    Configuration(
      Some("."),
      Seq(),
      Seq(),
      Map(),
      Some("output")) should be('valid)
  }

  private def makeRegex(s: String) = "(?s)" + s.
    replace("{", "\\{").
    replace("}", "\\}").
    replace("*", ".*")

  test("toString is nice and readabled for empty config") {
    Configuration(constraint = Set()).toString should fullyMatch regex (
      makeRegex("Configuration{*}"))
  }

  test("toString is nice and readabled for full config") {

    val expected = makeRegex(
      """Configuration{*classpath = ccc*includes = iii*excludes = eee*categories = ca*nnn*ppp*output = ooo*constraints = no cycles*}""")

    Configuration(
      classpath = Some("ccc"),
      includes = Seq("iii"),
      excludes = Seq("eee"),
      categories = Map("ca" -> Seq(NamedPattern("nnn", "ppp"))),
      output = Some("ooo"),
      constraint = Set(CycleFree)).
      toString should fullyMatch regex (expected)
  }

}