package de.schauderhaft.degraph.categorizer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node._

/**
 * categorizes an Inner class as content of its outer class
 */
object InternalClassCategorizer extends Function1[AnyRef, AnyRef] {
    override def apply(any: AnyRef): AnyRef = {
        any match {
            case cn: Node if (cn.nodeType == classType && cn.name.contains("$")) =>
                Node(classType, cn.name.split("""\$""")(0))
            case _ => any
        }
    }
}
