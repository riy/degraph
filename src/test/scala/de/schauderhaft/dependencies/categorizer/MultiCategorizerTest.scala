package de.schauderhaft.dependencies.categorizer
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import MultiCategorizer.combine

@RunWith(classOf[JUnitRunner])
class MultiCategorizerTest extends FunSuite with ShouldMatchers {
    test("combine of a single function returns list of the function result"){
    	combine(identity)("x") should be (Seq("x"))
    }
    test("combine of a multiple functions returns list of the function result"){
        val double = ( x : AnyRef)  => x.toString + x.toString 
    	combine(identity, double)("b") should be (Seq("b", "bb"))
    }
    
    test("category of a Seq is the tail of the Seq"){
        val dummy = (x : AnyRef) => throw new RuntimeException("this shouldn't get called")
        combine(identity, dummy)(Seq("a", "b", "c")) should be(Seq("b", "c"))
    }
    
    test("category of a Seq of lenght 1 is the Seq"){
    	val dummy = (x : AnyRef) => throw new RuntimeException("this shouldn't get called")
    	combine(identity, dummy)(Seq("a")) should be(Seq("a"))
    }
    
}