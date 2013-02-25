package de.schauderhaft.degraph.graph

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import NodeTestUtil.n
import de.schauderhaft.degraph.model.SimpleNode.classNode
import de.schauderhaft.degraph.model.SimpleNode.packageNode
import de.schauderhaft.degraph.slicer.PackageCategorizer

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
        val g = new Graph(PackageCategorizer)
        g.connect(classNode("de.p1.A1"), classNode("de.p2.B2"))
        g.connect(classNode("de.p2.B1"), classNode("de.p3.C2"))
        g.connect(classNode("de.p3.C1"), classNode("de.p1.A2"))

        g.edgesInCycles should be(Set(
            (packageNode("de.p1"), packageNode("de.p2")),
            (packageNode("de.p2"), packageNode("de.p3")),
            (packageNode("de.p3"), packageNode("de.p1"))))
    }
}