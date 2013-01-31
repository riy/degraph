package de.schauderhaft.degraph.categorizer

import de.schauderhaft.degraph.configuration.Pattern
import de.schauderhaft.degraph.configuration.NamedPattern
import de.schauderhaft.degraph.configuration.UnnamedPattern

object Slicer {
    def toSlicer(s: String, p: Pattern) = p match {
        case NamedPattern(p, n) => NamedPatternMatchingCategorizer(s, p, n)
        case UnnamedPattern(p) => PatternMatchingCategorizer(s, p)
    }
}