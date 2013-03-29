package de.schauderhaft.degraph.check

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.NodeTestUtil.n
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode.classNode
import de.schauderhaft.degraph.model.SimpleNode.packageNode
import de.schauderhaft.degraph.slicer.PackageCategorizer
import de.schauderhaft.degraph.slicer.ParallelCategorizer
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.configuration.CycleFree
import de.schauderhaft.degraph.graph.Graph
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CycleFreeTest extends FunSuite with ShouldMatchers {
    test("an empty graph has no cycles") {
        val g = new Graph()
        CycleFree.violations(g) should be(Set())
    }

    test("a graph with two cyclic dependent nodes without slices does not report those as cycles") {
        val g = new Graph()
        g.connect(n("a"), n("b"))
        g.connect(n("b"), n("a"))
        CycleFree.violations(g) should be(Set())
    }

    test("a graph with a cyclic dependency between to packages returns dependencies between those packages as cyclic") {
        val g = new Graph(PackageCategorizer)
        g.connect(classNode("de.p1.A1"), classNode("de.p2.B2"))
        g.connect(classNode("de.p2.B1"), classNode("de.p3.C2"))
        g.connect(classNode("de.p3.C1"), classNode("de.p1.A2"))

        CycleFree.violations(g) should be(Set(
            (packageNode("de.p1"), packageNode("de.p2")),
            (packageNode("de.p2"), packageNode("de.p3")),
            (packageNode("de.p3"), packageNode("de.p1"))))
    }

    test("detecting package cycles works with combined slices") {
        val g = new Graph(new ParallelCategorizer(PackageCategorizer, _ => SimpleNode("tld", "de")))
        g.connect(classNode("de.p1.A1"), classNode("de.p2.B2"))
        g.connect(classNode("de.p2.B1"), classNode("de.p3.C2"))
        g.connect(classNode("de.p3.C1"), classNode("de.p1.A2"))

        CycleFree.violations(g) should be(Set(
            (packageNode("de.p1"), packageNode("de.p2")),
            (packageNode("de.p2"), packageNode("de.p3")),
            (packageNode("de.p3"), packageNode("de.p1"))))
    }
}