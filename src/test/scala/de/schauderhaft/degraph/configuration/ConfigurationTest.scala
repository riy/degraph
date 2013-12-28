package de.schauderhaft.degraph.configuration

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._
import de.schauderhaft.degraph.model.ParentAwareNode
import de.schauderhaft.degraph.model.Node

@RunWith(classOf[JUnitRunner])
class ConfigurationTest extends FunSuite {

  test("valid configuration returns a Righ[Configuration]") {
    Configuration(Array[String]()).isRight should be(true)
  }

  test("classpath gets passed to analyzer") {
    val config = Configuration(Array("-c", "clp")).right.get
    val spy = new SpyAnalyze()
    config.copy(analyzer = spy).createGraph()
    spy.classPath should be("clp")
  }

  test("include / exclude gets passed to analyzer") {
    val config = Configuration(Array("-i", "input", "-e", "inputty")).right.get
    val spy = new SpyAnalyze()
    config.copy(analyzer = spy).createGraph()
    val filter = spy.filter
    filter(classNode("input")) should be(true)
    filter(classNode("inputty")) should be(false)
    filter(classNode("whats that")) should be(false)
  }

  test("the string after -f is considered a configuration file and gets loaded") {
    val config = Configuration(Array("-f", "src/test/resource/example.config")).right.get

    config.output should be(Some("o.graphml"))

    val spy = new SpyAnalyze()
    config.copy(analyzer = spy).createGraph()

    spy.classPath should be("""src/test/scala""")

    spy.filter(classNode("javax.swing.JPanel")) should be(false)
    spy.filter(classNode("de.schauderhaft")) should be(true)

    val categorizer = spy.categorizer
    categorizer(classNode("de.schauderhaft.degraph.parser.Jens")) should be(
      ParentAwareNode(packageNode("de.schauderhaft.degraph.parser"), SimpleNode("part", "parser"), SimpleNode("lib", "degraph"), SimpleNode("internalExternal", "internal")))
  }

  test("invalid configuration returns a Left(message)") {
    val message = Configuration(Array("garbage")).left.get
    message should include("garbage")
    message should include("Degraph")
  }

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

  class SpyAnalyze() extends AnalyzerLike {
    var classPath: String = ""
    var categorizer: Node => Node = (x) => x.asInstanceOf[Node]
    var filter: Node => Boolean = (_) => true

    def analyze(aClassPath: String,
      aCategorizer: Node => Node,
      aFilter: Node => Boolean) = {
      classPath = aClassPath
      categorizer = aCategorizer
      filter = aFilter
      new Graph()
    }
  }
}