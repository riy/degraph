package de.schauderhaft.degraph.categorizer

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ParallelCategorizerTest extends FunSuite with ShouldMatchers {
    test("parallel combination of zero functions returns the identity") {
        new ParallelCategorizer()("x") should be("x")
    }
    test("parallel combination of a single identity function returns the argument") {
        new ParallelCategorizer(identity)("x") should be("x")
    }

    test("parallel combination of a single function returns function value of the argument") {
        new ParallelCategorizer(ListCategory("a", "b", "c"))("b") should be("c")
    }

    test("parallel combination of two functions returns the results of the all functions (with identity)") {
        new ParallelCategorizer(ListCategory("a", "b"), ListCategory("b", "c"))("a") should be(ParentAwareNode("b", "a"))
    }

    test("parallel combination of two functions returns the results of the all functions") {
        new ParallelCategorizer(ListCategory("a", "b"), ListCategory("a", "c"))("a") should be(ParentAwareNode("b", "c"))
    }

    test("parallel combination of two functions returns the results of the all functions, with 3 functions") {
        new ParallelCategorizer(ListCategory("a", "b"), ListCategory("a", "c"), ListCategory("a", "d"))("a") should be(ParentAwareNode("b", "c", "d"))
    }

    test("steps through elements of a PackageAwareNode") {
        new ParallelCategorizer()(ParentAwareNode("a", "b", "c")) should be(ParentAwareNode("b", "c"))
    }

    test("steps through elements of a PackageAwareNode (single element)") {
        new ParallelCategorizer()(ParentAwareNode("a")) should be(ParentAwareNode("a"))
    }
}