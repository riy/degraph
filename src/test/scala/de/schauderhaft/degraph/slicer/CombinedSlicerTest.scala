package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.model.SimpleNode

@RunWith(classOf[JUnitRunner])
class CombinedSlicerTest extends FunSuite with ShouldMatchers {
    def n(s: String) = SimpleNode(s, s)

    test("the combined slicer of a single slicer is equivalent to the single slicer") {
        val slicer = new CombinedSlicer(ListCategory(n("a"), n("b")))
        slicer(n("a")) should be(n("b"))
        slicer(n("c")) should be(n("c"))
    }

    test("when the first slicer is applicable the other slicers get ignored") {
        val slicer = new CombinedSlicer(ListCategory(n("a"), n("b")), ListCategory(n("c"), n("d")))
        slicer(n("a")) should be(n("b"))
    }

    test("when the first slicer isn't applicable the other slicers get tried") {
        val slicer = new CombinedSlicer(ListCategory(n("a"), n("b")), ListCategory(n("c"), n("d")))
        slicer(n("c")) should be(n("d"))
    }
}