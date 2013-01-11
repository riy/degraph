package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import de.schauderhaft.degraph.analysis.Node._
import de.schauderhaft.degraph.analysis.Node

@RunWith(classOf[JUnitRunner])
class NamedPatternMatchingCategorizerTest extends FunSuite with ShouldMatchers {

    test("returns Node of type with name of match") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "(some.package.Class)", "name")
        categorizer(classNode("some.package.Class")) should be(Node("type", "name"))
    }
    test("returns input when not matched") {
        val categorizer = new NamedPatternMatchingCategorizer("type", "(y)", "name")
        categorizer(classNode("x")) should be(classNode("x"))
    }

}