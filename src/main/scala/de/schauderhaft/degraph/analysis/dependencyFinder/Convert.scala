package de.schauderhaft.degraph.analysis.dependencyFinder

import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.analysis.Node
import de.schauderhaft.degraph.analysis.Node._
import com.jeantessier.dependency.PackageNode

/**
 * converts objects as returned by Dependency Finder to the objects used by Degraph
 *
 */
object Convert {
    def apply(node: com.jeantessier.dependency.Node): Node = node match {
        case c: ClassNode => classNode(c.getName())
        case p: PackageNode => packageNode(p.getName())
        case _ => Node("unknonw", node.toString)
    }
}