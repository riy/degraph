package de.schauderhaft.dependencies.categorizer

object MultiCategorizer {
    def combine(categorizer : (AnyRef => AnyRef)*) : AnyRef => AnyRef = {
        x : AnyRef =>
            x match {
                case Seq(head)            => Seq(head)
                case Seq(head, rest @ _*) => rest
                case _                    => categorizer.map(_(x))
            }
    }
}