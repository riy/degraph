package de.schauderhaft.degraph.analysis.dependencyFinder

import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.analysis.Node
import com.jeantessier.dependency.PackageNode

object Convert {
    def apply(node: com.jeantessier.dependency.Node): Node = node match {
        case c: ClassNode => Node("Class", c.getName())
        case p: PackageNode => Node("Package", p.getName())
        case _ => Node("unknonw", node.toString)
    }
}