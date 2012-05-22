package de.schauderhaft.dependencies.categorizer

object MultiCategorizer {
    def combine(categorizer : (AnyRef => AnyRef)*) : AnyRef => AnyRef = {
        x : AnyRef =>
            next(categorizer, x)
    }

    private def next(categorizer : Seq[AnyRef => AnyRef], x : AnyRef) : AnyRef = {
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