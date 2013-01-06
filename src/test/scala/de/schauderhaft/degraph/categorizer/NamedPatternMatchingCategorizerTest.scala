package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import de.schauderhaft.degraph.analysis.Node._
import de.schauderhaft.degraph.analysis.Node

@RunWith(classOf[JUnitRunner])
class NamedPatternMatchingCategorizerTest extends FunSuite with ShouldMatchers {

    test("doesn't match arbitrary object") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "(some.package.Class)", "name")
        categorizer("x") should be("x")
    }

    test("matches fix pattern") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "(some.package.Class)", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
    }

    test("if no parens are give parens about the whole pattern are assumed") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.package.Class", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
    }

    test("fix pattern does not match other class") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "(some.package.Class)", "name")
        categorizer(classNode("some.other.package.Class")) should be(classNode("some.other.package.Class"))
    }

    test("returns only matched group") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.(package).Class", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
    }

    test("* matches arbitrary Letters ") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.(*).Class", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
        categorizer(classNode("some.mph.Class")) should be(Node("type", "name"))
    }

    test("* does not match dots ") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.(*).Class", "name")
        categorizer(classNode("some.pack.age.Class")) should be(classNode("some.pack.age.Class"))
    }

    test("dots match dots") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.pack.age.Class", "name")
        categorizer(classNode("some.pack.age.Class")) should be(Node("type", "name"))
    }

    test("dots don't match anything but dots") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.pack.age.Class", "name")
        categorizer(classNode("some.packxage.Class")) should be(classNode("some.packxage.Class"))
    }

    test("** matches single package level") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.(**).Class", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
    }

    test("** matches multiple package levels") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "some.(**).Class", "name")
        categorizer(classNode("some.pack.age.Class")) should be(Node("type", "name"))
    }

    test("*** throws an exception") {
        new NamedPatternMatchingCategorizer("type", "invalid***pattern", "name")
        pending
    }

    test("can handle weird characters in the pattern") {
        pending
        new NamedPatternMatchingCategorizer("type", """[^&|!"§$%&/()=?""", "name")
    }
}