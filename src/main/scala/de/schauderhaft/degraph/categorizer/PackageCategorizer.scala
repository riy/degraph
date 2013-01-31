package de.schauderhaft.degraph.categorizer
import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node._

/**
 * categorizes a java node as member of the matching package node
 */
object PackageCategorizer extends Function1[AnyRef, AnyRef] {
    def apply(value: AnyRef) = {
        value match {
            case Node(t, n) if (t == classType) => packageNode(packagePart(n))
            case _ => value
        }
    }

    def packagePart(name: String) = {
        val lastDotIndex = name.lastIndexOf('.')
        if (lastDotIndex >= 0)
            name.substring(0, lastDotIndex)
        else
            name
    }
}
