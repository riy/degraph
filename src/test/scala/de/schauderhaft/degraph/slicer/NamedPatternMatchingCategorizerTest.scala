package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._

import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode.classNode

@RunWith(classOf[JUnitRunner])
class NamedPatternMatchingCategorizerTest extends FunSuite {

  test("returns Node of type with name of match") {
    val categorizer = new NamedPatternMatchingCategorizer("type", "(some.package.Class)", "name")
    categorizer(classNode("some.package.Class")) should be(SimpleNode("type", "name"))
  }
  test("returns input when not matched") {
    val categorizer = new NamedPatternMatchingCategorizer("type", "(y)", "name")
    categorizer(classNode("x")) should be(classNode("x"))
  }

}