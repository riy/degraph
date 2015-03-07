package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.SimpleNode
import org.objectweb.asm._


object GraphBuildingClassVisitor {

  def classNode(slashSeparatedName: String): SimpleNode = {
    if (slashSeparatedName.contains(";"))
      new IllegalArgumentException("parameter has unexpected content. This is an internal error, please open a bug for degraph with this output: " + slashSeparatedName).printStackTrace()
    SimpleNode.classNode(slashSeparatedName.replace("/", "."))
  }

  def classNodeFromSingleType(singleTypeDescription: String) = {
    val Pattern = """\[*L([\w/$]+);""".r
    singleTypeDescription match {
      case Pattern(x) => classNode(x)
      case _ => classNode(singleTypeDescription)
    }
  }

  def classNodeFromDescriptor(desc: String): Set[SimpleNode] = {
    def internal: Set[SimpleNode] =
      if (desc == null || desc == "")
        Set()
      else {
        val pattern = """(?<=L)([\w/$]+)(?=[;<])""".r
        val matches = pattern.findAllIn(desc)
        matches.map(classNode(_)).toSet
      }


    internal
  }
}

class GraphBuildingClassVisitor(g: Graph) extends ClassVisitor(Opcodes.ASM5) {

  import de.schauderhaft.degraph.analysis.asm.GraphBuildingClassVisitor._


  class GraphBuildingAnnotationVisitor() extends AnnotationVisitor(api) {
    override def visit(name: String, value: AnyRef) = {
      value match {
        case t: Type => g.connect(currentClass, classNodeFromSingleType(t.getClassName))
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

    override def visitArray(name: String) = this
  }


  var currentClass: SimpleNode = null

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
        g.connect(currentClass, classNodeFromSingleType(aType))
      }

      override def visitFieldInsn(opcode: Int, owner: String, name: String, desc: String): Unit = {
        g.connect(currentClass, classNodeFromSingleType(owner))
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
      }

      override def visitMethodInsn(opcode: Int, owner: String, name: String, desc: String): Unit = {
        g.connect(currentClass, classNodeFromSingleType(owner))
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
      }

      override def visitMethodInsn(opcode: Int, owner: String, name: String, desc: String, itf: Boolean): Unit = {
        visitMethodInsn(opcode, owner, name, desc)
      }

      override def visitLdcInsn(cst: scala.Any): Unit = {
        cst match {
          case t: Type => classNodeFromDescriptor(t.getDescriptor).foreach(g.connect(currentClass, _))
          case _ =>
        }
      }

      override def visitTryCatchBlock(start: Label, end: Label, handler: Label, aType: String): Unit = {
        if (aType != null)
          g.connect(currentClass, classNode(aType))
      }

      override def visitLocalVariable(name: String, desc: String, signature: String, start: Label, end: Label, index: Int): Unit = {
        classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
        classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))
      }

    }

    classNodeFromDescriptor(signature).foreach(g.connect(currentClass, _))
    val asmType = Type.getType(desc)

    classNodeFromDescriptor(desc).foreach(g.connect(currentClass, _))
    if (exceptions != null)
      for {
        e <- exceptions
        cn <- classNodeFromDescriptor(e)
      } g.connect(currentClass, cn)
    new GraphBuildingMethodVisitor
  }
}
