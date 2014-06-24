package de.schauderhaft.degraph.app

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import de.schauderhaft.degraph.model.SimpleNode._
import scala.Some
import de.schauderhaft.degraph.model.{Node, SimpleNode, ParentAwareNode}
import de.schauderhaft.degraph.analysis.AnalyzerLike
import de.schauderhaft.degraph.graph.Graph

class ConfigurationFromCommandLineTest extends FunSuite {
  test("valid configuration returns a Righ[Configuration]") {
    ConfigurationFromCommandLine(Array[String]()).isRight should be(true)
  }

  test("classpath gets passed to analyzer") {
    val config = ConfigurationFromCommandLine(Array("-c", "clp")).right.get
    val spy = new SpyAnalyze()
    config.copy(analyzer = spy).createGraph()
    spy.classPath should be("clp")
  }

  test("include / exclude gets passed to analyzer") {
    val config = ConfigurationFromCommandLine(Array("-i", "input", "-e", "inputty")).right.get
    val spy = new SpyAnalyze()
    config.copy(analyzer = spy).createGraph()
    val filter = spy.filter
    filter(classNode("input")) should be(true)
    filter(classNode("inputty")) should be(false)
    filter(classNode("whats that")) should be(false)
  }

  test("the string after -f is considered a configuration file and gets loaded") {
    val config = ConfigurationFromCommandLine(Array("-f", "src/test/resource/example.config")).right.get

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
    val message = ConfigurationFromCommandLine(Array("garbage")).left.get
    message should include("garbage")
    message should include("Degraph")
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
