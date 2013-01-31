package de.schauderhaft.degraph.categorizer

class CombinedSlicer(slicer: (AnyRef => AnyRef)*) extends (AnyRef => AnyRef) {
    def apply(n: AnyRef): AnyRef = slicer.foldLeft(n)((x, s) => if (x != n) x else s(n))
}