package de.schauderhaft.degraph.slicer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._
import de.schauderhaft.degraph.model.Node

/**
 * categorizes an Inner class as content of its outer class
 */
object InternalClassCategorizer extends Function1[AnyRef, Node] {
    override def apply(any: AnyRef): Node = {
        any match {
            case cn: SimpleNode if (cn.nodeType == classType && cn.name.contains("$")) =>
                SimpleNode(classType, cn.name.split("""\$""")(0))
            case _ => any.asInstanceOf[Node]
        }
    }
}
