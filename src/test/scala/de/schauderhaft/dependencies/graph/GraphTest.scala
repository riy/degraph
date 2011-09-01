package de.schauderhaft.dependencies.graph
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite with ShouldMatchers{
	test("a new graph contains no top nodes"){
	    val g = new Graph()
	    g.topNodes should be ('empty)
	}

	test("a graph contains the nodes that get added to the graph"){
	    val g = new Graph()
	    val node = new AnyRef()
	    g.add(node)
	    g.topNodes should contain (node)
	}
}