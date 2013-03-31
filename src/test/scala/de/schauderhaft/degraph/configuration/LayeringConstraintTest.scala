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
class LayeringConstraintTest extends FunSuite with ShouldMatchers {
    test("simple violation free graph returns empty Set of violations") {
        val c = LayeringConstraint("t", IndexedSeq(Set("a"), Set("b"), Set("c")))
        c.violations(MockSliceSource("t", "a" -> "b", "b" -> "c")) should be(Set())
    }

    test("it's ok to skip layers") {
        val c = LayeringConstraint("t", IndexedSeq(Set("a"), Set("b"), Set("c")))
        c.violations(MockSliceSource("t", "a" -> "c")) should be(Set())
    }
    test("reverse dependency is reported as a violation") {
        val c = LayeringConstraint("t", IndexedSeq(Set("a"), Set("b"), Set("c")))
        c.violations(MockSliceSource("t", "b" -> "a")) should be(Set((SimpleNode("t", "b"), SimpleNode("t", "a"))))
    }

}

case class MockSliceSource(slice: String, deps: (String, String)*) extends SliceSource {
    implicit val factory = scalax.collection.edge.LkDiEdge
    val graph = SGraph[Node, LkDiEdge]()
    for ((a, b) <- deps)
        graph.addLEdge(SimpleNode(slice, a), SimpleNode(slice, b))(Graph.references)
    def slices = Set(slice)
    def slice(name: String) = if (name == slice) graph else SGraph[Node, LkDiEdge]()
}