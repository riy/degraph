package de.schauderhaft.degraph.analysis.dependencyFinder

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.slicer.MultiCategorizer._
import de.schauderhaft.degraph.slicer.ListCategory
import de.schauderhaft.degraph.model.SimpleNode
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers.be
import org.scalatest.matchers.ShouldMatchers.convertToAnyShouldWrapper

@RunWith(classOf[JUnitRunner])
class NoSelfReferenceTest extends FunSuite {
    import org.scalatest.matchers.ShouldMatchers._

    def n(s: String) = SimpleNode(s, s)

    test("returns true for unrelated objects") {
        new NoSelfReference()(n("a"), n("b")) should be(true)
    }

    test("returns false for identical objects") {
        new NoSelfReference()(n("a"), n("a")) should be(false)
    }

    test("returns false if second object is contained in Categories of first instance") {
        new NoSelfReference(ListCategory(n("a"), n("b"), n("c"), n("d")))(n("a"), n("c")) should be(false)
    }
    test("returns false if first object is con9tained in Categories of second instance") {
        new NoSelfReference(ListCategory(n("a"), n("b"), n("c"), n("d")))(n("c"), n("a")) should be(false)
    }

    test("returns true for unrelated objects with categories") {
        new NoSelfReference(ListCategory(n("a"), n("b"), n("c"), n("d")))(n("a"), n("x")) should be(true)
    }

    test("returns true for unrelated objects with common categorY") {
        new NoSelfReference(combine(ListCategory(n("a"), n("b")), ListCategory(n("x"), n("b"))))(n("a"), n("x")) should be(true)
    }
}