package de.schauderhaft.dependencies.categorizer
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode



@RunWith(classOf[JUnitRunner])
class InternalClassCategorizerTest extends FunSuite with ShouldMatchers {
    val cat = InternalClassCategorizer
    test("categorizes a simple class as it self") {
        val classNode = new ClassNode(new PackageNode("de.schauderhaft", true),"SomeClass",true)
        cat(classNode) should be (classNode)
    }
    
    test("categorizes a inner class as the outer class") {
    	val innerClassNode = new ClassNode(new PackageNode("de.schauderhaft", true),"SomeClass$SomeInnerClass",true)
    	val outerClassNode = new ClassNode(new PackageNode("de.schauderhaft", true),"SomeClass",true)
        cat(innerClassNode) should be (outerClassNode)
    }
    test("categorizes a nested inner class as the outer most class") {
    	val innerClassNode = new ClassNode(new PackageNode("de.schauderhaft", true),"SomeClass$SomeInnerClass$Of$Another$InnerClass",true)
    	val outerClassNode = new ClassNode(new PackageNode("de.schauderhaft", true),"SomeClass",true)
        cat(innerClassNode) should be (outerClassNode)
    }
}