package de.schauderhaft.degraph.graph

import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scalax.collection.mutable.{ Graph => SGraph }
import scalax.collection.edge.LkDiEdge
import de.schauderhaft.degraph.model.SimpleNode._
import de.schauderhaft.degraph.model.ParentAwareNode
import de.schauderhaft.degraph.model.SimpleNode
import Graph.contains

@RunWith(classOf[JUnitRunner])
class SliceNodeFinderTest extends FunSuite with ShouldMatchers {

    test("is not defined for an empty Graph") {
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

    test("is not defind if ParentAwareNode does not contain correct slice") {
        val p = packageNode("p")
        val n = new ParentAwareNode(p)
        val g = SGraph[AnyRef, LkDiEdge](n)
        val finder = new SliceNodeFinder("does not exist", g)
        finder.isDefinedAt(n) should be(false)
    }

    test("returns the matching slice from content of a ParentAwareNode ") {
        val p = packageNode("p")
        val n = new ParentAwareNode(SimpleNode("x", "x"), p, SimpleNode("y", "y"))
        val g = SGraph[AnyRef, LkDiEdge](n)
        val finder = new SliceNodeFinder(packageType, g)
        finder.isDefinedAt(n) should be(true)
        finder(n) should be(p)
    }

    test("traverses contains relationship") {
        implicit val factory = scalax.collection.edge.LkDiEdge
        val p = packageNode("p")
        val g = SGraph[AnyRef, LkDiEdge]()
        g.addLEdge(p, "x")(contains)
        val finder = new SliceNodeFinder(packageType, g)
        finder.isDefinedAt("x") should be(true)
        finder("x") should be(p)
    }

    test("returns correct element from slices relationship") {
        implicit val factory = scalax.collection.edge.LkDiEdge
        val p = packageNode("p")
        val c = classNode("p.c")
        val g = SGraph[AnyRef, LkDiEdge]()
        g.addLEdge(p, c)(contains)
        val finder = new SliceNodeFinder(packageType, g)
        finder.isDefinedAt(c) should be(true)
        finder(c) should be(p)
    }

}