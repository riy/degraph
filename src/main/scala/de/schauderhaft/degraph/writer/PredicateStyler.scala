package de.schauderhaft.degraph.writer

import de.schauderhaft.degraph.model.Node

object PredicateStyler {
    def styler(predicate: ((Node, Node)) => Boolean, highlight: EdgeStyle, default: EdgeStyle): (((Node, Node)) => EdgeStyle) =
        (t: (Node, Node)) => if (predicate(t)) highlight else default
}