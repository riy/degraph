package de.schauderhaft.degraph.filter

import de.schauderhaft.degraph.model.Node

/**
 * returns true if the include set is empty or at least one element returns true,
 * and all of the exclude elements returns false.
 */
class IncludeExcludeFilter(
    include: Set[Node => Boolean],
    exclude: Set[Node => Boolean])
    extends (Node => Boolean) {

    override def apply(x: Node) = {
        include_?(x) && !exclude.exists(_(x))
    }

    private def include_?(x: Node) =
        if (include.isEmpty)
            true
        else include.exists(_(x))
}