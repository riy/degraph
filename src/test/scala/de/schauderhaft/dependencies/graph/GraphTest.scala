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

	test("a simple node has no content"){
	    val g = new Graph()
	    val node = new AnyRef()
	    g.add(node)
	    g.contentsOf(node) should be ('empty)
	}
	
	test("if an added node is contained in a category, that category gets added"){
	    val category = new AnyRef()
	    val node = new AnyRef()
	    val g = new Graph(_ => category)
	    g.add(node)
	    g.topNodes should contain (category)
	} 
	
	test("elements of a category are contained in that category"){
	    val g = new Graph(_ => "x")
	    val node = new AnyRef() 
	    g.add(node)
	    g.contentsOf("x") should contain (node)
	}
	
	test("elements of a not existing category are the empty set"){
	    new Graph().contentsOf("x") should be ('empty)
	}
	
	test("categories that are part of other categories contain each other" ){
	    val topCategory = new AnyRef()
	    val subCategory = new AnyRef()
	    val node = new AnyRef()
	    val g = new Graph(Map(node->subCategory).withDefaultValue(topCategory))
	    g.add(node)
	    
	    g.topNodes should equal (Set(topCategory))
	    g.contentsOf(topCategory) should equal (Set(subCategory))
	    g.contentsOf(subCategory) should equal (Set(node))
	}

	test("edges can get added for not existing nodes"){
	    val g = new Graph()
	    val a = new AnyRef()
	    val b = new AnyRef()
	    g.connect(a, b)
	    g.connectionsOf(a) should be (Set(b))
	    g.topNodes should be (Set(a,b))
	}
	
	test("simple nodes don't have connections"){
		val g = new Graph()
		val a = new AnyRef()
		g.add(a)
		
		g.connectionsOf(a) should be (Set())
	}
}