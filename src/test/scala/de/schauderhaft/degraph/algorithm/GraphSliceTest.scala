package de.schauderhaft.degraph.algorithm

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import de.schauderhaft.degraph.graph.Graph
import scalax.collection.{ Graph => SGraph }
import de.schauderhaft.degraph.slicer.PackageCategorizer
import de.schauderhaft.degraph.model.Node._
import scalax.collection.edge.Implicits._
import scalax.collection.edge.LkDiEdge

@RunWith(classOf[JUnitRunner])
class GraphSliceTest extends FunSuite with ShouldMatchers {

    test("the package slice of a graph without package is empty") {
        val g = new Graph()
        g.add("x")

        g.slice("package") should be(SGraph())
    }

    test("the package slice of a graph with some nodes in a single package is that package") {
        val g = new Graph(category = PackageCategorizer)
        g.add(classNode("p.C"))

        g.slice(packageType) should be(SGraph(packageNode("p")))
    }

    test("the package slice of a graph with two connected nodes in two packages will be that two packages") {
        pending
        val g = new Graph(category = PackageCategorizer)
        g.connect(classNode("p.one.Class"), classNode("p.two.Class"))

        g.slice(packageType) should be(SGraph((packageNode("p.one") ~+#> packageNode("p.two"))("references")))
    }
}