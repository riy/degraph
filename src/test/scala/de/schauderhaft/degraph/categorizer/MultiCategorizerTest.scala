package de.schauderhaft.degraph.categorizer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import MultiCategorizer.combine
import de.schauderhaft.degraph.categorizer.MultiCategorizer.combine
import de.schauderhaft.degraph.filter.ListCategory

@RunWith(classOf[JUnitRunner])
class MultiCategorizerTest extends FunSuite with ShouldMatchers {
    test("combine of a single identity function returns the argument") {
        combine(identity)("x") should be("x")
    }
    test("combine of a single function returns function value of the argument") {
        combine(ListCategory("a", "b", "c"))("b") should be("c")
    }

    test("combine of two functions returns the result of the first function if it is not equal to the argument") {
        combine(ListCategory("a", "b"), ListCategory("b", "c"))("b") should be("c")
    }

}