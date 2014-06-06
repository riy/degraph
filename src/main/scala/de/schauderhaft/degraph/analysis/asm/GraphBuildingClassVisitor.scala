package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.graph.Graph
import org.objectweb.asm._
import de.schauderhaft.degraph.model.SimpleNode


object GraphBuildingClassVisitor {

  def classNode(slashSeparatedName: String): SimpleNode = SimpleNode.classNode(slashSeparatedName.replace("/", "."))

  def classNodeFromDescriptor(desc: String): Set[SimpleNode] = {
    def internal: Set[SimpleNode] =
      if (desc == null || desc == "")
        Set()
      else {
        val pattern = """(?<=L)([\w/$]+)(?=[;<])""".r
        val matches = pattern.findAllIn(desc)
        (if (matches.isEmpty) Set(desc) else matches).map(classNode(_)).toSet
      }
    val probs = Set(
      //"(III)V",
      "(DD)J")
    val result = internal
    val problem = result.find((n) => probs.contains(n.name))
    if (problem.nonEmpty){
      println
      println("problem: " + problem.get)
      println("input: " + desc)
      new RuntimeException().printStackTrace(System.out)
    }

    result
  }
}

class GraphBuildingClassVisitor(g: Graph) extends ClassVisitor(Opcodes.ASM4) {

  import GraphBuildingClassVisitor._


  class GraphBuildingAnnotationVisitor() extends AnnotationVisitor(api) {
    override def visit(name: String, value: AnyRef) = {
      value match {
        case t: Type => g.connect(currentClass, classNode(t.getClassName))
        case _ =>
      }
    }

    override def visitAnnotation(name: String, desc: String) = {
      classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
      this
    }

    override def visitEnum(name: String, desc: String, value: String) {
      classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
    }
  }


  private def log[T](t: T): T = {
    t
  }


  var currentClass: SimpleNode = null;

  override def visit(version: Int, access: Int, name: String, signature: String, superName: String, interfaces: Array[String]): Unit = {
    currentClass = classNode(name)

    // finds type parameters
    classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))

    if (superName == null) // happens only for Object I guess
      g.add(currentClass)
    else
      g.connect(currentClass, classNode(superName))

    for (i <- interfaces)
      g.connect(currentClass, classNode(i))
  }

  override def visitSource(source: String, debug: String): Unit = super.visitSource(source, debug)

  override def visitOuterClass(owner: String, name: String, desc: String): Unit = {
    g.add(classNode(owner))
    classNodeFromDescriptor(desc).foreach(g.add(_))
  }

  override def visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor = {


    classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))

    new GraphBuildingAnnotationVisitor()
  }

  override def visitAttribute(attr: Attribute): Unit = super.visitAttribute(attr)

  override def visitInnerClass(name: String, outerName: String, innerName: String, access: Int): Unit = {
    if (outerName == null) // what the heck does that mean?
      g.add(classNode(name))
    else {
      // we don't gather this dependency, but deduce it through the naming ... not sure how good that idea is.
      // g.connect(classNode(name), classNode(outerName))
    }
  }

  override def visitField(access: Int, name: String, desc: String, signature: String, value: scala.Any): FieldVisitor = {
    class GraphBuildingFieldVisitor extends FieldVisitor(api) {
      override def visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor = {
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
        new GraphBuildingAnnotationVisitor()
      }
    }

    classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))
    classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
    new GraphBuildingFieldVisitor
  }

  override def visitMethod(access: Int, name: String, desc: String, signature: String, exceptions: Array[String]): MethodVisitor = {
    class GraphBuildingMethodVisitor extends MethodVisitor(api) {
      override def visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor = {
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
        new GraphBuildingAnnotationVisitor()
      }

      override def visitParameterAnnotation(parameter: Int,
                                            desc: String,
                                            visible: Boolean): AnnotationVisitor = {
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
        new GraphBuildingAnnotationVisitor()
      }

      override def visitTypeInsn(opcode: Int, aType: String): Unit = {
        classNodeFromDescriptor(aType).foreach(g.connect(currentClass, _))
      }

      override def visitFieldInsn(opcode: Int, owner: String, name: String, desc: String): Unit = {
        classNodeFromDescriptor(owner).foreach(g.connect(currentClass, _))
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
      }

      override def visitMethodInsn(opcode: Int, owner: String, name: String, desc: String): Unit = {
        classNodeFromDescriptor(owner).foreach(g.connect(currentClass, _))
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
      }

      override def visitLdcInsn(cst: scala.Any): Unit = {
        cst match {
          case t: Type => classNodeFromDescriptor(t.getDescriptor).foreach(g.connect(currentClass, _))
          case _ =>
        }
      }

      override def visitTryCatchBlock(start: Label, end: Label, handler: Label, aType: String): Unit = {
        classNodeFromDescriptor(aType).foreach(g.connect(currentClass, _))
      }

      override def visitLocalVariable(name: String, desc: String, signature: String, start: Label, end: Label, index: Int): Unit = {
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
        classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))
      }
    }

    classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))
    classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
    if (exceptions != null)
      for {
        e <- exceptions
        cn <- classNodeFromDescriptor(e)
      } g.connect(currentClass, cn)
    new GraphBuildingMethodVisitor
  }
}
