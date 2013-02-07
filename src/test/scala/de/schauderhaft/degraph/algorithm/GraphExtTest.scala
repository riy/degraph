package de.schauderhaft.degraph.algorithm

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalax.collection.Graph

@RunWith(classOf[JUnitRunner])
class GraphExtTest extends FunSuite with ShouldMatchers {

    test("a filtered empty graph is an empty graph") {
        val g = Graph()
        val g2 = g.filter(_ => true)

        g should be(g2)
    }
}