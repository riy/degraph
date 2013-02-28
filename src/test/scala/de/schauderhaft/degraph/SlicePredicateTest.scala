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
        val p = new SlicePredicate(n => n, Set((classNode("a"), classNode("b"))))
        p(classNode("x"), classNode("y")) should be(false)
        p(classNode("a"), classNode("b")) should be(true)
        p(classNode("b"), classNode("a")) should be(false)
    }

    test("returns true for edge with the slice projection contained in set of edges in cycles") {
        val p = new SlicePredicate(
            Map(n("x") -> n("a"),
                n("y") -> n("b"),
                n("a") -> n("x"),
                n("b") -> n("y")),
            Set((n("x"), n("y"))))
        p(n("x"), n("y")) should be(false)
        p(n("a"), n("b")) should be(true)
        p(n("b"), n("a")) should be(false)
    }

}