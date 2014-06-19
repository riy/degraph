package de.schauderhaft.degraph.configuration

import de.schauderhaft.degraph.slicer.NamedPatternMatchingCategorizer
import de.schauderhaft.degraph.slicer.PatternMatchingCategorizer

object Slicer {
    def toSlicer(s: String, p: Pattern) = p match {
        case NamedPattern(p, n) => NamedPatternMatchingCategorizer(s, p, n)
        case UnnamedPattern(p) => PatternMatchingCategorizer(s, p)
    }
}