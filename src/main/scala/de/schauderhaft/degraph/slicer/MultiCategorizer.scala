package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.Node

/**
 * combines multiple Categorizers to a single one by applying one after the other until one succeeds to categorize the node.
 */
object MultiCategorizer {
    def combine(categorizer: (AnyRef => Node)*): AnyRef => Node = {
        x: AnyRef =>
            next(categorizer, x)
    }

    private def next(categorizer: Seq[AnyRef => Node], x: AnyRef): Node = {
        if (categorizer.isEmpty)
            x.asInstanceOf[Node]
        else {
            val cat = categorizer.head(x)
            if (cat != x) cat
            else
                next(categorizer.tail, x)
        }
    }

}