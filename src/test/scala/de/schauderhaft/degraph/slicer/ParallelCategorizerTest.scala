package de.schauderhaft.degraph.slicer

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.model.ParentAwareNode
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.Node

@RunWith(classOf[JUnitRunner])
class ParallelCategorizerTest extends FunSuite {

  def n(s: String) = SimpleNode(s, s)
  val id = (n: AnyRef) => n.asInstanceOf[Node]

  test("parallel combination of zero functions returns the identity") {
    new ParallelCategorizer()(n("x")) should be(n("x"))
  }
  test("parallel combination of a single identity function returns the argument") {
    new ParallelCategorizer(id)(n("x")) should be(n("x"))
  }

  test("parallel combination of a single function returns function value of the argument") {
    new ParallelCategorizer(ListCategory(n("a"), n("b"), n("c")))(n("b")) should be(n("c"))
  }

  test("parallel combination of two functions returns the results of the all functions (with identity)") {
    new ParallelCategorizer(ListCategory(n("a"), n("b")), ListCategory(n("b"), n("c")))(n("a")) should be(ParentAwareNode(n("b"), n("a")))
  }

  test("parallel combination of two functions returns the results of the all functions") {
    new ParallelCategorizer(ListCategory(n("a"), n("b")), ListCategory(n("a"), n("c")))(n("a")) should be(ParentAwareNode(n("b"), n("c")))
  }

  test("parallel combination of two functions returns the results of the all functions, with 3 functions") {
    new ParallelCategorizer(ListCategory(n("a"), n("b")), ListCategory(n("a"), n("c")), ListCategory(n("a"), n("d")))(n("a")) should be(ParentAwareNode(n("b"), n("c"), n("d")))
  }

  test("steps through elements of a PackageAwareNode") {
    new ParallelCategorizer()(ParentAwareNode(n("a"), n("b"), n("c"))) should be(ParentAwareNode(n("b"), n("c")))
  }

  test("steps through elements of a PackageAwareNode (single element)") {
    new ParallelCategorizer()(ParentAwareNode(n("a"))) should be(ParentAwareNode(n("a")))
  }
}