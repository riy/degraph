package de.schauderhaft.degraph.writer

import de.schauderhaft.degraph.model.Node

class SlicePredicate(
    slicing: Node => Node,
    edgesInCycles: Set[(Node, Node)])
    extends (((Node, Node)) => Boolean) {

    def apply(e: (Node, Node)): Boolean = {
        def sliceChain(n: Node): Set[Node] = sliceChainIter(n, Set())
        def sliceChainIter(n: Node, chain: Set[Node]): Set[Node] = {
            val s = slicing(n)
            if (chain.contains(s))
                chain
            else
                sliceChainIter(s, chain + s)
        }

        val edgeCandidates = for {
            n1 <- sliceChain(e._1)
            n2 <- sliceChain(e._2)
        } yield (n1, n2)

        edgeCandidates.exists(e => edgesInCycles.exists(eic => e._1.contains(eic._1) && e._2.contains(eic._2)))
    }

}