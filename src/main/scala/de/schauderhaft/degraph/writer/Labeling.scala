package de.schauderhaft.degraph.writer

object Labeling {
    def apply(node: AnyRef, parent: Option[AnyRef] = None): String = parent match {
        case Some(p) =>
            val pLabel = apply(p) + "."
            val nLabel = apply(node)
            if (nLabel.startsWith(pLabel))
                nLabel.replace(pLabel, "")
            else
                nLabel
        case _ => node.toString
    }
}