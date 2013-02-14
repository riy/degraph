package de.schauderhaft.degraph.graph

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.edge.LkDiEdge
import de.schauderhaft.degraph.model.Node.packageNode
import de.schauderhaft.degraph.model.Node.packageType
import de.schauderhaft.degraph.slicer.ParentAwareNode

@RunWith(classOf[JUnitRunner])
class SliceNodeFinderTest extends FunSuite with ShouldMatchers {

    test("isDefinedAt is false for an empty Graph") {
        val finder = new SliceNodeFinder("x", SGraph[AnyRef, LkDiEdge]())
        finder.isDefinedAt("z") should be(false)
    }

    test("returns the node for a slice node") {
        val p = packageNode("p")
        val g = SGraph[AnyRef, LkDiEdge](p)
        val finder = new SliceNodeFinder(packageType, g)
        finder.isDefinedAt(p) should be(true)
        finder(p) should be(p)
    }

    test("is not defined if  node is of different slice") {
        val p = packageNode("p")
        val g = SGraph[AnyRef, LkDiEdge](p)
        val finder = new SliceNodeFinder("does not exist", g)
        finder.isDefinedAt(p) should be(false)
    }

    test("returns the content of a ParentAwareNode ") {
        val p = packageNode("p")
        val n = new ParentAwareNode(p)
        val g = SGraph[AnyRef, LkDiEdge](n)
        val finder = new SliceNodeFinder(packageType, g)
        finder.isDefinedAt(n) should be(true)
        finder(n) should be(p)
    }

}