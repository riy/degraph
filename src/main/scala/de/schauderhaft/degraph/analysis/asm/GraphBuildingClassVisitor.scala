package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.graph.Graph
import org.objectweb.asm._
import de.schauderhaft.degraph.model.SimpleNode

class GraphBuildingClassVisitor(g: Graph) extends ClassVisitor(Opcodes.ASM4) {

  private def log[T](t: T): T = {
    println(t)
    t
  }

  private def classNode(slashSeparatedName: String): SimpleNode = SimpleNode.classNode(slashSeparatedName.replace("/", "."))

  private def classNodeFromDescriptor(desc: String): Option[SimpleNode] = {
    if (desc.startsWith("["))
      classNodeFromDescriptor(desc.substring(1))
    else if (desc.startsWith("L"))
      Some(classNode(desc.substring(1, desc.length-1)))
    else
      None
  }

  var currentClass: SimpleNode = null;

  override def visit(version: Int, access: Int, name: String, signature: String, superName: String, interfaces: Array[String]): Unit = {
    currentClass = classNode(name)
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
  }

  override def visitAnnotation(desc: String, visible: Boolean): AnnotationVisitor = {

    class GraphBuildingAnnotationVisitor() extends AnnotationVisitor(api) {
      //TODO nested Annotations, and values
      override def visit(name: String, value: AnyRef) = {

      }

      override def visitAnnotation(name: String, desc: String) = {
        g.connect(currentClass, classNode(name))
        this
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
