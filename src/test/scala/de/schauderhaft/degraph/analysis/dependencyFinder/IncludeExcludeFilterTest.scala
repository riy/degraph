package de.schauderhaft.degraph.analysis.dependencyFinder

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.NodeTestUtil.n

@RunWith(classOf[JUnitRunner])
class IncludeExcludeFilterTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    val alwaysTrue = (_: AnyRef) => true
    val alwaysFalse = (_: AnyRef) => false

    test("true whithout filter") {
        new IncludeExcludeFilter(
            Set(), Set())(n("x")) should be(true)
    }

    test("true when any include filter returns true") {
        new IncludeExcludeFilter(
            Set(alwaysFalse, alwaysTrue, alwaysFalse), Set())(n("x")) should be(true)
    }

    test("false when all include filters returns false") {
        new IncludeExcludeFilter(
            Set(alwaysFalse, alwaysFalse, alwaysFalse), Set())(n("x")) should be(false)
    }

    test("false when any exclude filter returns true") {
        new IncludeExcludeFilter(
            Set(),
            Set(alwaysFalse, alwaysTrue, alwaysFalse))(n("x")) should be(false)
    }

}