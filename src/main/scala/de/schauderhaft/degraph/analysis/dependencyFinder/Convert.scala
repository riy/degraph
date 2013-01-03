package de.schauderhaft.degraph.analysis.dependencyFinder

import com.jeantessier.dependency.ClassNode
import de.schauderhaft.degraph.analysis.Node
import com.jeantessier.dependency.PackageNode

object Convert {
    private def dotify(name: String) = if (name.isEmpty()) name else name + "."
    def apply(node: ClassNode): Node = Node("Class", dotify(node.getPackageNode().getName) + node.getName())
    def apply(node: PackageNode): Node = Node("Package", node.getName())
}