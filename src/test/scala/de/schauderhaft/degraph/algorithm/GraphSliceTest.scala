package de.schauderhaft.degraph.algorithm

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalax.collection.{ Graph => SGraph }
import scalax.collection.GraphPredef.EdgeLikeIn

@RunWith(classOf[JUnitRunner])
class GraphSliceTest extends FunSuite with ShouldMatchers {

    class SlicingGraph[N, E[X] <: EdgeLikeIn[X]](g: SGraph[N, E]) {
        def slice(name: String): SGraph[N, E] = g
    }

    object SlicingGraph {
        implicit def graph2ExtGraph[N, E[X] <: EdgeLikeIn[X]](sg: SGraph[N, E]): SlicingGraph[N, E] = new SlicingGraph[N, E](sg)
    }

    test("a sliced empty graph is an empty graph") {
        import SlicingGraph._

        val g = SGraph()
        new SlicingGraph[Nothing, Nothing](g).slice("package") should be(SGraph())
    }
}