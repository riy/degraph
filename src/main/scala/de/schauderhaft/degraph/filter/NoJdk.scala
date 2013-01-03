package de.schauderhaft.degraph.filter

import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.analysis.Node

/**
 * filters out all jdk classes
 */
object NoJdk extends (AnyRef => Boolean) {
    def apply(value: AnyRef) = {
        value match {
            case n: Node if (Set("Package", "Class")(n.nodeType)) => !isJdk(n.name)
            case cn: ClassNode => !isJdk(cn.getPackageNode())
            case pn: PackageNode => !isJdk(pn)
            case _ => true
        }
    }

    private def isJdk(node: PackageNode): Boolean = isJdk(node.getName())

    private def isJdk(name: String): Boolean =
        name.startsWith("java.") ||
            name.startsWith("javax.")
}