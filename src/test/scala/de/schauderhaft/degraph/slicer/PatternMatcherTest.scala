package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class PatternMatcherTest extends FunSuite with ShouldMatchers {

    test("doesn't match arbitrary object") {
        val matcher = new PatternMatcher("(some.package.Class)")
        matcher.matches("x") should be(None)
    }

    test("matches fix pattern") {
        val matcher = new PatternMatcher("(some.package.Class)")
        matcher.matches("some.package.Class") should be(Some("some.package.Class"))
    }

    test("if no parens are give parens about the whole pattern are assumed") {
        val matcher = new PatternMatcher("some.package.Class")
        matcher.matches("some.package.Class") should be(Some("some.package.Class"))
    }

    test("fix pattern does not match other class") {
        val matcher = new PatternMatcher("(some.package.Class)")
        matcher.matches("some.other.package.Class") should be(None)
    }

    test("returns only matched group") {
        val matcher = new PatternMatcher("some.(package).Class")
        matcher.matches("some.package.Class") should be(Some("package"))
    }

    test("* matches arbitrary Letters ") {
        val matcher = new PatternMatcher("some.(*).Class")
        matcher.matches("some.package.Class") should be(Some("package"))
        matcher.matches("some.mph.Class") should be(Some("mph"))
    }

    test("* does not match dots ") {
        val matcher = new PatternMatcher("some.(*).Class")
        matcher.matches("some.pack.age.Class") should be(None)
    }

    test("dots match dots") {
        val matcher = new PatternMatcher("some.pack.age.Class")
        matcher.matches("some.pack.age.Class") should be(Some("some.pack.age.Class"))
    }

    test("dots don't match anything but dots") {
        val matcher = new PatternMatcher("some.pack.age.Class")
        matcher.matches("some.packxage.Class") should be(None)
    }

    test("** matches single package level") {
        val matcher = new PatternMatcher("some.(**).Class")
        matcher.matches("some.package.Class") should be(Some("package"))
    }

    test("** matches multiple package levels") {
        val matcher = new PatternMatcher("some.(**).Class")
        matcher.matches("some.pack.age.Class") should be(Some("pack.age"))
    }

    test("*** throws an exception") {
        val caught = evaluating {
            val matcher = new PatternMatcher("invalid***pattern")
            matcher.matches("blah")
        } should produce[IllegalArgumentException]

        caught.getMessage() should be("More than two '*'s in a row is not a supported pattern.")
    }

}