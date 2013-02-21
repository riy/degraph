package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.Node

class ListCategory(list: List[Node]) extends (AnyRef => Node) {
    def apply(v: AnyRef) = {
        val i = list.indexOf(v)
        if (i >= 0 && i < list.size - 1)
            list(i + 1)
        else v match {
            case n: Node => n
        }
    }
}

object ListCategory {
    def apply(args: Node*) = new ListCategory(args.toList)
}