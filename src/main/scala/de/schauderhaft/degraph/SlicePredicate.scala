package de.schauderhaft.degraph

import de.schauderhaft.degraph.model.Node

class SlicePredicate(
    slicing: Node => Node,
    edgesInCycles: Set[(Node, Node)])
    extends (((Node, Node)) => Boolean) {

    def apply(e: (Node, Node)): Boolean = {
        edgesInCycles((e._1, slicing(e._2))) ||
            edgesInCycles((slicing(e._1), e._2)) ||
            edgesInCycles((e._1, e._2)) ||
            edgesInCycles((slicing(e._1), slicing(e._2)))
    }

}