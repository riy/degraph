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

case class MockSliceSource(slice: String, deps: (String, String)*) extends SliceSource {
    implicit val factory = scalax.collection.edge.LkDiEdge
    val graph = SGraph[Node, LkDiEdge]()
    for ((a, b) <- deps)
        graph.addLEdge(SimpleNode(slice, a), SimpleNode(slice, b))(Graph.references)
    def slices = Set(slice)
    def slice(name: String) = if (name == slice) graph else SGraph[Node, LkDiEdge]()
}