package de.schauderhaft.dependencies

import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

object NoJdk extends (AnyRef => Boolean) {
    def apply(value : AnyRef) = {
        println(value)
        value match {
            case cn : ClassNode   => !isJdk(cn.getPackageNode())
            case pn : PackageNode => !isJdk(pn)
            case _                => true
        }
    }

    private def isJdk(node : PackageNode) =
        node.getName().startsWith("java.") ||
            node.getName().startsWith("javax.")
}