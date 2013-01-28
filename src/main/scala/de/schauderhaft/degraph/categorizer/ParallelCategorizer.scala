package de.schauderhaft.degraph.categorizer

class ParallelCategorizer(cs: (AnyRef => AnyRef)*) extends (AnyRef => AnyRef) {
    def apply(x: AnyRef): AnyRef = cs match {
        case _ if (cs.isEmpty) => x
        case _ => cs.head(x) match {
            case y if (y == x) => x
            case y => ParentAwareNode(y)
        }
    }
}

case class ParentAwareNode(vals: AnyRef*)
