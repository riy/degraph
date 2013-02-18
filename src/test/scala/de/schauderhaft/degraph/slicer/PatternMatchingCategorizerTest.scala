package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode.classNode

@RunWith(classOf[JUnitRunner])
class PatternMatchingCategorizerTest extends FunSuite with ShouldMatchers {

    test("doesn't match arbitrary object") {
        val categorizer = new PatternMatchingCategorizer("type", "(some.package.Class)")
        categorizer("x") should be("x")
    }

    test("returns only matched group") {
        val categorizer = new PatternMatchingCategorizer("type", "some.(package).Class")
        categorizer(classNode("some.package.Class")) should be(SimpleNode("type", "package"))
    }
}