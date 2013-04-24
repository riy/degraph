package de.schauderhaft.degraph.configuration

import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import de.schauderhaft.degraph.graph.SliceSource
import scalax.collection.edge.LkDiEdge
import scalax.collection.mutable.{ Graph => SGraph }
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.check.LenientLayer
import de.schauderhaft.degraph.check.DirectLayeringConstraint

@RunWith(classOf[JUnitRunner])
class DirectLayeringConstraintTest extends FunSuite with ShouldMatchers {

    val c = DirectLayeringConstraint("t", IndexedSeq(LenientLayer("a"), LenientLayer("b"), LenientLayer("c")))

    test("simple violation free graph returns empty Set of violations") {
        c.violations(MockSliceSource("t", "a" -> "b", "b" -> "c")) should be(Set())
    }

    test("it's not ok to skip layers") {
        val violations = c.violations(MockSliceSource("t", "a" -> "c"))

        violations.flatMap(_.dependencies.toSet) should be(Set((SimpleNode("t", "a"), SimpleNode("t", "c"))))
    }

    test("reverse dependency is reported as a violation") {
        val violations = c.violations(MockSliceSource("t", "b" -> "a"))

        violations.flatMap(_.dependencies.toSet) should be(Set((SimpleNode("t", "b"), SimpleNode("t", "a"))))
    }
    test("dependencies in other layers are ignored") {
        c.violations(MockSliceSource("x", "b" -> "a")) should be(Set())
    }

    test("dependency to unknown is ok when from last") {
        c.violations(MockSliceSource("t", "c" -> "x")) should be(Set())
    }

    test("dependency from unknown is ok when to first") {
        c.violations(MockSliceSource("t", "x" -> "a")) should be(Set())
    }
    test("dependency to unknown is not ok when from middle") {
        val violations = c.violations(MockSliceSource("t", "b" -> "x"))

        violations.flatMap(_.dependencies.toSet) should be(Set((SimpleNode("t", "b"), SimpleNode("t", "x"))))
    }

    test("dependency from unknown is not ok when to middle") {
        val violations = c.violations(MockSliceSource("t", "x" -> "b"))
        violations.flatMap(_.dependencies.toSet) should be(Set((SimpleNode("t", "x"), SimpleNode("t", "b"))))
    }
}
