package de.schauderhaft.degraph.categorizer
import com.jeantessier.dependency.ClassNode

object PackageCategorizer extends Function1[AnyRef, AnyRef] {
    def apply(value : AnyRef) = {
        value match {
            case cn : ClassNode => cn.getPackageNode()
            case _              => value
        }
    }
}
