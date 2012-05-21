package de.schauderhaft.dependencies.categorizer
import com.jeantessier.dependency.ClassNode

object InternalClassCategorizer extends Function1[AnyRef, AnyRef] {
    override def apply(any : AnyRef) : AnyRef = {
        any match {
            case cn : ClassNode if (cn.getName().contains("$")) =>
                new ClassNode(cn.getPackageNode(), cn.getName().split("""\$""")(0), true)
            case _ => any
        }
    }
}
