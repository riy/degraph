package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.SimpleNode

class PatternMatchingFilter(pattern: String) extends (Node => Boolean) {
    private val matcher = new PatternMatcher(pattern)
    def apply(n: Node) = n match {
        case sn: SimpleNode => matcher.matches(sn.name).isDefined
        case _ => true
    }
}