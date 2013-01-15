package de.schauderhaft.degraph.configuration

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.analysis.dependencyFinder.AnalyzerLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node.packageNode

@RunWith(classOf[JUnitRunner])
class ConfigurationTest extends FunSuite with ShouldMatchers {

    test("valid configuration returns a Righ[Configuration]") {
        Configuration(Array[String]()).isRight should be(true)
    }

    test("classpath gets passed to analyzer") {
        val config = Configuration(Array("-c", "clp")).right.get
        val spy = new SpyAnalyze()
        config.createGraph(spy)
        spy.classPath should be("clp")
    }

    test("include / exclude gets passed to analyzer") {
        val config = Configuration(Array("-i", "input", "-e", "inputty")).right.get
        val spy = new SpyAnalyze()
        config.createGraph(spy)
        val filter = spy.filter
        filter("input") should be(true)
        filter("inputty") should be(false)
        filter("whats that") should be(false)
    }

    test("categorizer gets passed to analyzer") {
        val config = Configuration(Array("-g", "*.(*).*")).right.get
        val spy = new SpyAnalyze()
        config.createGraph(spy)
        val categorizer = spy.categorizer
        categorizer(packageNode("jens.schauderhaft.de")) should be(Node("default", "schauderhaft"))
    }

    test("invalid configuration returns a Left(message)") {
        val message = Configuration(Array("garbage")).left.get
        message should include("garbage")
        message should include("Degraph")
    }

    test("a configuration with out classpath to analyze is not valid") {
        Configuration(
            None,
            Seq(),
            Seq(),
            Map(),
            Some("output")) should not be ('valid)
    }

    test("a configuration with out output to analyze is not valid") {
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

    class SpyAnalyze() extends AnalyzerLike {
        var classPath: String = ""
        var categorizer: AnyRef => AnyRef = (x) => x
        var filter: AnyRef => Boolean = (_) => true

        def analyze(aClassPath: String,
            aCategorizer: AnyRef => AnyRef,
            aFilter: AnyRef => Boolean) = {
            classPath = aClassPath
            categorizer = aCategorizer
            filter = aFilter
            new Graph()
        }
    }
}