package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._

import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode.classNode

@RunWith(classOf[JUnitRunner])
class PatternMatchingCategorizerTest extends FunSuite {
  def n(s: String) = SimpleNode(s, s)
  test("doesn't match arbitrary object") {
    val categorizer = new PatternMatchingCategorizer("type", "(some.package.Class)")
    categorizer(n("x")) should be(n("x"))
  }

  test("returns only matched group") {
    val categorizer = new PatternMatchingCategorizer("type", "some.(package).Class")
    categorizer(classNode("some.package.Class")) should be(SimpleNode("type", "package"))
  }
}