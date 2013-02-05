package de.schauderhaft.degraph.graph

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import de.schauderhaft.degraph.slicer.MultiCategorizer._
import de.schauderhaft.degraph.slicer.ListCategory
@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite with ShouldMatchers {

    test("a new graph contains no top nodes") {
        val g = new Graph()
        g.topNodes should be('empty)
    }

    test("a graph contains the nodes that get added to the graph") {
        val g = new Graph()
        val node = new AnyRef()
        g.add(node)
        g.topNodes should contain(node)
    }

    test("a simple node has no content") {
        val g = new Graph()
        val node = new AnyRef()
        g.add(node)
        g.contentsOf(node) should be('empty)
    }

    test("if an added node is contained in a category, that category gets added") {
        val category = new AnyRef()
        val node = new AnyRef()
        val g = new Graph(_ => category)
        g.add(node)
        g.topNodes should contain(category)
    }

    test("elements of a category are contained in that category") {
        val g = new Graph(_ => "x", _ => true)
        val node = new AnyRef()
        g.add(node)
        g.contentsOf("x") should contain(node)
    }

    test("elements of a not existing category are the empty set") {
        new Graph().contentsOf("x") should be('empty)
    }

    test("categories that are part of other categories contain each other") {
        val topCategory = new AnyRef()
        val subCategory = new AnyRef()
        val node = new AnyRef()
        val g = new Graph(Map(node -> subCategory).withDefaultValue(topCategory), _ => true)
        g.add(node)

        g.topNodes should equal(Set(topCategory))
        g.contentsOf(topCategory) should equal(Set(subCategory))
        g.contentsOf(subCategory) should equal(Set(node))
    }

    test("edges can get added for not existing nodes") {
        val g = new Graph()
        val a = new AnyRef()
        val b = new AnyRef()
        g.connect(a, b)
        g.connectionsOf(a) should be(Set(b))
        g.topNodes should be(Set(a, b))
    }

    test("connectionsOf returns all connected nodes") {
        val g = new Graph()
        val a = new AnyRef()
        val b = new AnyRef()
        val c = new AnyRef()
        g.connect(a, b)
        g.connect(a, c)
        g.connectionsOf(a) should be(Set(b, c))
        g.topNodes should be(Set(a, b, c))
    }

    test("simple nodes don't have connections") {
        val g = new Graph()
        val a = new AnyRef()
        g.add(a)

        g.connectionsOf(a) should be(Set())
    }

    test("allNodes of an empty graph is the empty Set") {
        val g = new Graph()
        g.allNodes should be(Set())
    }

    test("allNodes of a graph without categories are the topNodes") {
        val g = new Graph()
        g.add("a")
        g.add("23")
        g.allNodes should be(g.topNodes)
        g.allNodes should be(Set("a", "23"))
    }

    test("allNodes of a graph with categories contains the topNodes and all categories") {
        val g = new Graph(combine(ListCategory("a", "b", "c"), ListCategory("23", "42", "c")))
        g.add("a")
        g.add("23")
        g.allNodes should be(Set("a", "b", "c", "23", "42"))
    }

    test("categories don't get filtert") {
        val g = new Graph(ListCategory("a", "b"), _ == "a")
        g.add("a")
        g.topNodes should be(Set("b"))
    }

    test("an empty graph has no cycles") {
        val g = new Graph()
        g.edgesInCycles should be(Set())
    }

    test("a graph with two cyclic dependent nodes has both edges in cycles") {
        val g = new Graph()
        g.connect("a", "b")
        g.connect("b", "a")
        g.edgesInCycles should be(Set(("a", "b"), ("b", "a")))

    }
}