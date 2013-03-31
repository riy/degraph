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

@RunWith(classOf[JUnitRunner])
class MultiElementLayerConstraintTest extends FunSuite with ShouldMatchers {
    val c = LayeringConstraint("t", IndexedSeq(Set("a"), Set("b", "c", "d"), Set("e")))

    test("dependencies into a multielement layer are ok") {
        c.violations(MockSliceSource("t", "a" -> "b", "a" -> "d")) should be(Set())
    }

    test("dependencies from a multielement layer are ok") {
        c.violations(MockSliceSource("t", "b" -> "e", "d" -> "e")) should be(Set())
    }

    test("dependencies within a multielement layer are ok") {
        c.violations(MockSliceSource("t", "b" -> "c", "b" -> "d")) should be(Set())
    }
}
