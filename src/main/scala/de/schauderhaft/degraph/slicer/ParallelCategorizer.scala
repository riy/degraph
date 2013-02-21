package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.ParentAwareNode
import de.schauderhaft.degraph.model.Node

class ParallelCategorizer(cs: (AnyRef => Node)*) extends (AnyRef => Node) {
    def apply(x: AnyRef): Node = x match {
        case pan: ParentAwareNode => pan.next
        case _ =>
            cs match {
                case _ if (cs.isEmpty) => x.asInstanceOf[Node]
                case _ => ParentAwareNode(cs.map(_(x)): _*).prune
            }
    }

}

