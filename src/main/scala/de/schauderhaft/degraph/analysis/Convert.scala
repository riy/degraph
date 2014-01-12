package de.schauderhaft.degraph.analysis

import com.jeantessier.dependency.ClassNode
import com.jeantessier.dependency.PackageNode
import de.schauderhaft.degraph.model.SimpleNode._
import de.schauderhaft.degraph.model.SimpleNode

/**
 * converts objects as returned by Dependency Finder to the objects used by Degraph
 *
 */
object Convert {
    def apply(node: com.jeantessier.dependency.Node): SimpleNode = node match {
        case c: ClassNode => classNode(c.getName())
        case p: PackageNode => packageNode(p.getName())
        case _ => SimpleNode("unknonw", node.toString)
    }
}