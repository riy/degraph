package de.schauderhaft.degraph.slicer

/**
 * combines multiple Categorizers to a single one by applying one after the other until one succeeds to categorize the node.
 */
object MultiCategorizer {
    def combine(categorizer: (AnyRef => AnyRef)*): AnyRef => AnyRef = {
        x: AnyRef =>
            next(categorizer, x)
    }

    private def next(categorizer: Seq[AnyRef => AnyRef], x: AnyRef): AnyRef = {
        if (categorizer.isEmpty)
            x
        else {
            val cat = categorizer.head(x)
            if (cat != x) cat
            else
                next(categorizer.tail, x)
        }
    }

}