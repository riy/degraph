package de.schauderhaft.degraph.categorizer

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ParallelCategorizerTest extends FunSuite with ShouldMatchers {
    test("combine of zero functions returns the identity") {
        new ParallelCategorizer()("x") should be("x")
    }
    test("combine of a single identity function returns the argument") {
        new ParallelCategorizer(identity)("x") should be("x")
    }

    test("combine of a single function returns function value of the argument") {
        new ParallelCategorizer(ListCategory("a", "b", "c"))("b") should be(ParentAwareNode("c"))
    }

    test("combine of a single function returns cascading function value of the argument") {
        pending
        new ParallelCategorizer(ListCategory("a", "b", "c", "d"))("b") should be(ParentAwareNode("c", "d"))
    }
    //
    //    test("combine of two functions returns the result of the first function if it is not equal to the argument") {
    //        combine(ListCategory("a", "b"), ListCategory("b", "c"))("b") should be("c")
    //    }
}