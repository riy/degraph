package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.graph.Graph
import org.objectweb.asm._
import de.schauderhaft.degraph.model.SimpleNode

class MyClassVisitor(g : Graph) extends ClassVisitor(Opcodes.ASM4){
  override def visit(version: Int, access: Int, name: String, signature: String, superName: String, interfaces: Array[String]): Unit = super.visit(version, access, name, signature, superName, interfaces)

  override def visitSource(source: String, debug: String): Unit = super.visitSource(source, debug)

  override def visitOuterClass(owner: String, name: String, desc: String): Unit = {
    println("visitOuter"+ owner + " " + name + " " + desc)
    g.add(SimpleNode.classNode(owner.replace("/",".")))
  }

  override def visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor = super.visitAnnotation(desc, visible)

  override def visitAttribute(attr: Attribute): Unit = super.visitAttribute(attr)

  override def visitInnerClass(name: String, outerName: String, innerName: String, access: Int): Unit = {
  println("visit inner"  + name + " " + outerName + " "+ innerName + " ")
  }

  override def visitField(access: Int, name: String, desc: String, signature: String, value: scala.Any): FieldVisitor = super.visitField(access, name, desc, signature, value)

  override def visitMethod(access: Int, name: String, desc: String, signature: String, exceptions: Array[String]): MethodVisitor = super.visitMethod(access, name, desc, signature, exceptions)

  override def visitEnd(): Unit = super.visitEnd()
}
