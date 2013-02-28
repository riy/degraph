package de.schauderhaft.degraph.filter

import de.schauderhaft.degraph.model.Node

/**
 * a filter that prevents dependencies from an entity to itself
 */
class NoSelfReference(category: Node => Node = x => x) extends (((Node, Node)) => Boolean) {
    def apply(t: (Node, Node)): Boolean = {
        !(
            findInCategory(t._1, t._2) ||
            findInCategory(t._2, t._1))
    }

    private def findInCategory(a: Node, b: Node): Boolean = {
        val next = category(a)
        a == b || (next != a && findInCategory(next, b))
    }
}
