package de.schauderhaft.degraph.writer

object PredicateStyler {
    def styler(predicate: ((AnyRef, AnyRef)) => Boolean, highlight: EdgeStyle, default: EdgeStyle): (((AnyRef, AnyRef)) => EdgeStyle) =
        (t: (AnyRef, AnyRef)) => if (predicate(t)) highlight else default
}