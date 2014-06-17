package de.schauderhaft.degraph.analysis.asm

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import GraphBuildingClassVisitor._
import org.objectweb.asm.Type

@RunWith(classOf[JUnitRunner])
class GraphBuildingClassVisitorTest extends FunSuite {


  test("no node for null") {
    val result = classNodeFromDescriptor(null)
    result.isEmpty should be(true)
  }

  test("no node for empty String") {
    val result = classNodeFromDescriptor("")
    result.isEmpty should be(true)
    result should be ('empty)
  }


  test("create a simple class node") {
    val result = GraphBuildingClassVisitor.classNode("java/lang/System")
    result should be(classNode("java.lang.System"))

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

  test("complex example") {

    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("Lscala/runtime/AbstractFunction1<Lscala/Tuple2<Ljava/lang/String;Lorg/rogach/scallop/Scallop;>;Ljava/lang/Object;>;Lscala/Serializable;")
    result should contain(classNode("scala/runtime/AbstractFunction1"))
    result should contain(classNode("scala/Tuple2"))
    result should contain(classNode("java/lang/String"))
    result should contain(classNode("org/rogach/scallop/Scallop"))
    result should contain(classNode("java/lang/Object"))
    result should contain(classNode("scala/Serializable"))
  }


  test("inner classes") {

    val result = GraphBuildingClassVisitor.classNodeFromDescriptor(
      "<T:Ljava/lang/Object;>(TT;)Lorg/scalautils/TripleEqualsSupport$LegacyCheckingEqualizer<TT;>;")
    result should contain(classNode("java/lang/Object"))
    result should contain(classNode("org/scalautils/TripleEqualsSupport$LegacyCheckingEqualizer"))
    result should not contain (classNode(""))
    result should not contain (classNode("org/scalautils/TripleEqualsSupport$egacyCheckingEqualizer"))
  }


  test("empty names") {
    val result = GraphBuildingClassVisitor.classNodeFromDescriptor(
      "<A:Ljava/lang/Object;B:Ljava/lang/Object;S:Ljava/lang/Object;>(Lorg/scalatest/prop/TableFor19<TA;TO;>;Lscala/Function19<TA;TB;TS;Lscala/runtime/BoxedUnit;>;)V")
    result should contain(classNode("java/lang/Object"))
    result should contain(classNode("org/scalatest/prop/TableFor19"))
    result should contain(classNode("scala/runtime/BoxedUnit"))
    result should not contain (classNode(""))
  }

  test("(DD)I"){
    val asmType = Type.getType("(DD)I")
    println(asmType.getArgumentTypes.toList)
    println(asmType.getElementType)
    println(asmType.getReturnType)
    val inti = Type.getType("I")
    println (inti.getClassName)
  }

  test("primitive types get ignored"){
    val result = GraphBuildingClassVisitor.classNodeFromDescriptor("(DD)I")

    result should be (empty)
  }
}
