package de.schauderhaft.degraph.filter

import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._

/**
 * filters out all jdk classes
 */
object NoJdk extends (AnyRef => Boolean) {
    def apply(value: AnyRef) = {
        value match {
            case n: Node if (Set(packageType, classType)(n.nodeType)) => !isJdk(n.name)
            case _ => true
        }
    }

    private def isJdk(name: String): Boolean =
        name.startsWith("java.") ||
            name.startsWith("javax.")
}