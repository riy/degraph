package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class CombinedSlicerTest extends FunSuite with ShouldMatchers {

    test("the combined slicer of a single slicer is equivalent to the single slicer") {
        val slicer = new CombinedSlicer(ListCategory("a", "b"))
        slicer("a") should be("b")
        slicer("c") should be("c")
    }

    test("when the first slicer is applicable the other slicers get ignored") {
        val slicer = new CombinedSlicer(ListCategory("a", "b"), ListCategory("c", "d"))
        slicer("a") should be("b")
    }

    test("when the first slicer isn't applicable the other slicers get tried") {
        val slicer = new CombinedSlicer(ListCategory("a", "b"), ListCategory("c", "d"))
        slicer("c") should be("d")
    }
}