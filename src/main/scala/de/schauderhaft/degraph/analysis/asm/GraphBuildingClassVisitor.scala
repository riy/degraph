package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.graph.Graph
import org.objectweb.asm._
import de.schauderhaft.degraph.model.SimpleNode


object GraphBuildingClassVisitor {

  def classNode(slashSeparatedName: String): SimpleNode = SimpleNode.classNode(slashSeparatedName.replace("/", "."))

  def classNodeFromDescriptor(desc: String): Set[SimpleNode] = {
    if (desc == null)
      Set()
    else {
      val pattern = """(?<=L)([\w/]*)(?=[;<])""".r
      pattern.findAllIn(desc).map(classNode(_)).toSet
    }
  }
}

class GraphBuildingClassVisitor(g: Graph) extends ClassVisitor(Opcodes.ASM4) {

  import GraphBuildingClassVisitor._

  private def log[T](t: T): T = {
    println(t)
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

  override def visitField(access: Int, name: String, desc: String, signature: String, value: scala.Any): FieldVisitor = super.visitField(access, name, desc, signature, value)

  override def visitMethod(access: Int, name: String, desc: String, signature: String, exceptions: Array[String]): MethodVisitor = super.visitMethod(access, name, desc, signature, exceptions)

  override def visitEnd(): Unit = super.visitEnd()
}
