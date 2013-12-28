package de.schauderhaft.degraph.slicer

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._

import de.schauderhaft.degraph.model.SimpleNode.classNode

@RunWith(classOf[JUnitRunner])
class InternalClassCategorizerTest extends FunSuite {
  val cat = InternalClassCategorizer
  test("categorizes a simple class as it self") {
    val clazzNode = classNode("de.schauderhaft.SomeClass")
    cat(clazzNode) should be(clazzNode)
  }

  test("categorizes a inner class as the outer class") {
    val innerClassNode = classNode("de.schauderhaft.SomeClass$SomeInnerClass")
    val outerClassNode = classNode("de.schauderhaft.SomeClass")
    cat(innerClassNode) should be(outerClassNode)
  }
  test("categorizes a nested inner class as the outer most class") {
    val innerClassNode = classNode("de.schauderhaft.SomeClass$SomeInnerClass$Of$Another$InnerClass")
    val outerClassNode = classNode("de.schauderhaft.SomeClass")
    cat(innerClassNode) should be(outerClassNode)
  }
}