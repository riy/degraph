package de.schauderhaft.dependencies.filter

class ListCategory(list : List[AnyRef]) extends (AnyRef => AnyRef) {
    def apply(v : AnyRef) = {
        val i = list.indexOf(v)
        if (i >= 0 && i < list.size - 1)
            list(i + 1)
        else
            v
    }
}

object ListCategory {
    def apply(args : AnyRef*) = new ListCategory(args.toList)
}