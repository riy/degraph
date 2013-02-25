package de.schauderhaft.degraph.graph

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import NodeTestUtil.n
import de.schauderhaft.degraph.model.SimpleNode.classNode

@RunWith(classOf[JUnitRunner])
class GraphCycleTest extends FunSuite with ShouldMatchers {
    test("an empty graph has no cycles") {
        val g = new Graph()
        g.edgesInCycles should be(Set())
    }

    test("a graph with two cyclic dependent nodes has both edges in cycles") {
        val g = new Graph()
        g.connect(n("a"), n("b"))
        g.connect(n("b"), n("a"))
        g.edgesInCycles should be(Set((n("a"), n("b")), (n("b"), n("a"))))
    }

    test("a graph with a cyclic dependency between to packages returns dependencies between those packages as cyclic") {
        pending
        val g = new Graph()
        g.connect(classNode("de.p1.A"), classNode("de.p2.B"))
    }
}