package de.schauderhaft.degraph.analysis.asm

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import GraphBuildingClassVisitor._

@RunWith(classOf[JUnitRunner])
class GraphBuildingClassVisitorTest extends FunSuite {

  test("no node for garbage") {
    val result = classNodeFromDescriptor("xxxxx")
    result.isEmpty should be(true)
  }

  test("no node for null") {
    val result = classNodeFromDescriptor(null)
    result.isEmpty should be(true)
  }

  test("no node for empty String") {
    val result = classNodeFromDescriptor("")
    result.isEmpty should be(true)
  }

  test("identifies simple class") {
    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("Ljava/lang/String;")
    result should contain(classNode("java.lang.String"))
  }

  test("identifies simple class as Array") {
    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("[Ljava/lang/String;")
    result should contain(classNode("java.lang.String"))
  }

  test("finds method with argument and return value") {
    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("(ILjava/lang/String;)[Ljava/blah/Blubb;")
    result should contain(classNode("java.lang.String"))
    result should contain(classNode("java.blah.Blubb"))
  }

  test("complex example"){

    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("Lscala/runtime/AbstractFunction1<Lscala/Tuple2<Ljava/lang/String;Lorg/rogach/scallop/Scallop;>;Ljava/lang/Object;>;Lscala/Serializable;")
    result should contain(classNode("scala/runtime/AbstractFunction1"))
    result should contain(classNode("scala/Tuple2"))
    result should contain(classNode("java/lang/String"))
    result should contain(classNode("org/rogach/scallop/Scallop"))
    result should contain(classNode("java/lang/Object"))
    result should contain(classNode("scala/Serializable"))
  }
}
