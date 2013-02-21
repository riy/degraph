package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.Node

class CombinedSlicer(slicer: (AnyRef => Node)*) extends (AnyRef => Node) {
    def apply(n: AnyRef): Node = slicer.foldLeft(n.asInstanceOf[Node])((x, s) => if (x != n) x else s(n))
}