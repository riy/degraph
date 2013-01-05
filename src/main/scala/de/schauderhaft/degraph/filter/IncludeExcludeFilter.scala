package de.schauderhaft.degraph.filter

class IncludeExcludeFilter(
    include: Set[AnyRef => Boolean],
    exclude: Set[AnyRef => Boolean])
    extends (AnyRef => Boolean) {

    override def apply(x: AnyRef) = {
        include_?(x) && !exclude.exists(_(x))
    }

    private def include_?(x: AnyRef) =
        if (include.isEmpty)
            true
        else include.exists(_(x))
}