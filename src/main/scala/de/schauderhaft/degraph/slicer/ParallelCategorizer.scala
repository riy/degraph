package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.ParentAwareNode

class ParallelCategorizer(cs: (AnyRef => AnyRef)*) extends (AnyRef => AnyRef) {
    def apply(x: AnyRef): AnyRef = x match {
        case pan: ParentAwareNode => pan.next
        case _ =>
            cs match {
                case _ if (cs.isEmpty) => x
                case _ => ParentAwareNode(cs.map(_(x)): _*).prune
            }
    }

}

