package de.schauderhaft.degraph.filter

/**
 * returns true if the include set is empty or at least one element returns true,
 * and all of the exclude elements returns false.
 */
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