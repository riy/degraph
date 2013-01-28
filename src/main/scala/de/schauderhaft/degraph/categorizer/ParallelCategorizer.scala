package de.schauderhaft.degraph.categorizer

class ParallelCategorizer(cs: (AnyRef => AnyRef)*) extends (AnyRef => AnyRef) {
    def apply(x: AnyRef): AnyRef = cs match {
        case _ if (cs.isEmpty) => x
        case _ => ParentAwareNode(cs.map(_(x)): _*).prune
    }
}

case class ParentAwareNode(vals: AnyRef*) {
    def prune = if (vals.size == 1) vals.head else this
}
