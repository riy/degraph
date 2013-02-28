package de.schauderhaft.degraph

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.model.SimpleNode.classNode
import de.schauderhaft.degraph.graph.NodeTestUtil.n

@RunWith(classOf[JUnitRunner])
class SlicePredicateTest extends FunSuite with ShouldMatchers {

    test("returns true for edge contained in set of edges in cycles") {
        val predicate = new SlicePredicate(n => n, Set((classNode("a"), classNode("b"))))
        predicate(classNode("x"), classNode("y")) should be(false)
        predicate(classNode("a"), classNode("b")) should be(true)
        predicate(classNode("b"), classNode("a")) should be(false)
    }

    test("returns true for edge with the slice projection contained in set of edges in cycles") {
        val predicate = new SlicePredicate(
            Map(n("x") -> n("a"),
                n("y") -> n("b"),
                n("a") -> n("x"),
                n("b") -> n("y")),
            Set((n("x"), n("y"))))
        predicate(n("a"), n("b")) should be(true)
        predicate(n("b"), n("a")) should be(false)
    }

    test("returns true for edge in set but with the slice projection NOT contained in set of edges in cycles") {
        val predicate = new SlicePredicate(
            Map(n("x") -> n("a"),
                n("y") -> n("b"),
                n("a") -> n("x"),
                n("b") -> n("y")),
            Set((n("x"), n("y"))))
        predicate(n("x"), n("y")) should be(true)
        predicate(n("a"), n("y")) should be(true)
        predicate(n("x"), n("b")) should be(true)
        predicate(n("a"), n("b")) should be(true)
    }

    test("returns true when slice projection is parentAwareNode containing an element of edges in cycles") {
        pending
    }
}