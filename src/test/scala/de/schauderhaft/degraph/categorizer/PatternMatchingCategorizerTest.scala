package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import de.schauderhaft.degraph.analysis.Node._
import de.schauderhaft.degraph.analysis.Node

@RunWith(classOf[JUnitRunner])
class PatternMatchingCategorizerTest extends FunSuite with ShouldMatchers {

    test("doesn't match arbitrary object") {
        val categorizer = new PatternMatchingCategorizer("type", "(some.package.Class)")
        categorizer("x") should be("x")
    }

    test("matches fix pattern") {
        val categorizer = new PatternMatchingCategorizer("type", "(some.package.Class)")
        categorizer(classNode("some.package.Class")) should be(Node("type", "some.package.Class"))
    }

    test("if no parens are give parens about the whole pattern are assumed") {
        val categorizer = new PatternMatchingCategorizer("type", "some.package.Class")
        categorizer(classNode("some.package.Class")) should be(Node("type", "some.package.Class"))
    }

    test("fix pattern does not match other class") {
        val categorizer = new PatternMatchingCategorizer("type", "(some.package.Class)")
        categorizer(classNode("some.other.package.Class")) should be(classNode("some.other.package.Class"))
    }

    test("returns only matched group") {
        val categorizer = new PatternMatchingCategorizer("type", "some.(package).Class")
        categorizer(classNode("some.package.Class")) should be(Node("type", "package"))
    }

    test("* matches arbitrary Letters ") {
        val categorizer = new PatternMatchingCategorizer("type", "some.(*).Class")
        categorizer(classNode("some.package.Class")) should be(Node("type", "package"))
        categorizer(classNode("some.mph.Class")) should be(Node("type", "mph"))
    }

    test("* does not match dots ") {
        val categorizer = new PatternMatchingCategorizer("type", "some.(*).Class")
        categorizer(classNode("some.pack.age.Class")) should be(classNode("some.pack.age.Class"))
    }

    test("dots match only dots positive") {
        val categorizer = new PatternMatchingCategorizer("type", "some.pack.age.Class")
        categorizer(classNode("some.pack.age.Class")) should be(Node("type", "some.pack.age.Class"))
    }

    test("dots match only dots negative") {
        val categorizer = new PatternMatchingCategorizer("type", "some.pack.age.Class")
        categorizer(classNode("some.package.Class")) should be(classNode("some.package.Class"))
    }

}