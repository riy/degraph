package de.schauderhaft.dependencies.filter

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class IncludeExcludeFilterTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    val alwaysTrue = (_ : AnyRef) => true
    val alwaysFalse = (_ : AnyRef) => false

    test("true whithout filter") {
        new IncludeExcludeFilter(
            Set(), Set())("x") should be(true)
    }

    test("true when any include filter returns true") {
        new IncludeExcludeFilter(
            Set(alwaysFalse, alwaysTrue, alwaysFalse), Set())("x") should be(true)
    }

    test("false when all include filters returns false") {
        new IncludeExcludeFilter(
            Set(alwaysFalse, alwaysFalse, alwaysFalse), Set())("x") should be(false)
    }

    test("false when any exclude filter returns true") {
        new IncludeExcludeFilter(
            Set(),
            Set(alwaysFalse, alwaysTrue, alwaysFalse))("x") should be(false)
    }

}