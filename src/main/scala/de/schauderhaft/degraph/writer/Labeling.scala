package de.schauderhaft.degraph.writer

import de.schauderhaft.degraph.analysis.Node

/**
 * creates a labels from an arbitrary node. It starts with the name of actual Nodes and to String if anything else ends up in the Graph.
 *
 * It then removes the part of the name that is identically with the label of the parent node if present.
 */
object Labeling {
    def apply(node: AnyRef, parent: Option[AnyRef] = None): String = parent match {
        case Some(p) =>
            val pLabel = apply(p) + "."
            val nLabel = apply(node)
            if (nLabel.startsWith(pLabel))
                nLabel.replace(pLabel, "")
            else
                nLabel
        case _ => baseLabel(node)
    }

    private def baseLabel(node: AnyRef) = node match {
        case n: Node => n.name
        case _ => node.toString
    }
}