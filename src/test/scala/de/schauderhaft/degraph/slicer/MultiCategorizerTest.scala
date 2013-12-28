package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._
import MultiCategorizer.combine
import de.schauderhaft.degraph.slicer.MultiCategorizer.combine
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.Node

@RunWith(classOf[JUnitRunner])
class MultiCategorizerTest extends FunSuite {

  private def n(s: String) = SimpleNode(s, s)
  private val id: AnyRef => Node = x => x.asInstanceOf[Node]

  test("combine of a single identity function returns the argument") {
    combine(id)(n("x")) should be(n("x"))
  }
  test("combine of a single function returns function value of the argument") {
    combine(ListCategory(n("a"), n("b"), n("c")))(n("b")) should be(n("c"))
  }

  test("combine of two functions returns the result of the first function if it is not equal to the argument") {
    combine(ListCategory(n("a"), n("b")), ListCategory(n("b"), n("c")))(n("b")) should be(n("c"))
  }
}