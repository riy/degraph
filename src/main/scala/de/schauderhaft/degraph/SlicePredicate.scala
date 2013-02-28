package de.schauderhaft.degraph

import de.schauderhaft.degraph.model.Node

class SlicePredicate(
    slicing: Node => Node,
    edgesInCycles: Set[(Node, Node)])
    extends (((Node, Node)) => Boolean) {

    def apply(e: (Node, Node)): Boolean = {
        def nodeAndSlice(n: Node) = Set(n, slicing(n))

        val edgeCandidates = for {
            n1 <- nodeAndSlice(e._1)
            n2 <- nodeAndSlice(e._2)
        } yield (n1, n2)

        edgeCandidates.exists(e => edgesInCycles(e._1, e._2))
    }

}