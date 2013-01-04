package de.schauderhaft.degraph.categorizer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

object InternalClassCategorizer extends Function1[AnyRef, AnyRef] {
    override def apply(any: AnyRef): AnyRef = {
        any match {
            case cn: Node if (cn.nodeType == classType && cn.name.contains("$")) =>
                Node(classType, cn.name.split("""\$""")(0))
            case _ => any
        }
    }
}
