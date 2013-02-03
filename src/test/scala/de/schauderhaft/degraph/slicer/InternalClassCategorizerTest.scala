package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

import de.schauderhaft.degraph.model.Node.classNode



@RunWith(classOf[JUnitRunner])
class InternalClassCategorizerTest extends FunSuite with ShouldMatchers {
    val cat = InternalClassCategorizer
    test("categorizes a simple class as it self") {
        val clazzNode = classNode("de.schauderhaft.SomeClass")
        cat(clazzNode) should be (clazzNode)
    }
    
    test("categorizes a inner class as the outer class") {
    	val innerClassNode = classNode("de.schauderhaft.SomeClass$SomeInnerClass")
    	val outerClassNode = classNode("de.schauderhaft.SomeClass")
        cat(innerClassNode) should be (outerClassNode)
    }
    test("categorizes a nested inner class as the outer most class") {
    	val innerClassNode = classNode("de.schauderhaft.SomeClass$SomeInnerClass$Of$Another$InnerClass")
    	val outerClassNode = classNode("de.schauderhaft.SomeClass")
        cat(innerClassNode) should be (outerClassNode)
    }
}