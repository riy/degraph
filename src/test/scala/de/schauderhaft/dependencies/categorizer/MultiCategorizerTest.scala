package de.schauderhaft.dependencies.categorizer
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import MultiCategorizer.combine
import de.schauderhaft.dependencies.filter.ListCategory

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