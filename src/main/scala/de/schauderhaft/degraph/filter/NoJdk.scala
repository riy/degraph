package de.schauderhaft.degraph.filter

import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode

/**
 * filters out all jdk classes
 */
object NoJdk extends (AnyRef => Boolean) {
    def apply(value : AnyRef) = {
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