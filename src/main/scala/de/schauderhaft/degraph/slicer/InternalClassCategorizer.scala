package de.schauderhaft.degraph.slicer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._

/**
 * categorizes an Inner class as content of its outer class
 */
object InternalClassCategorizer extends Function1[AnyRef, AnyRef] {
    override def apply(any: AnyRef): AnyRef = {
        any match {
            case cn: SimpleNode if (cn.nodeType == classType && cn.name.contains("$")) =>
                SimpleNode(classType, cn.name.split("""\$""")(0))
            case _ => any
        }
    }
}
