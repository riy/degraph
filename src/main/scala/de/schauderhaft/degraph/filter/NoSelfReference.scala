package de.schauderhaft.degraph.filter

/**
 * a filter that prevents dependencies from an entity to itself
 */
class NoSelfReference(category : AnyRef => AnyRef = x => x) extends (((AnyRef, AnyRef)) => Boolean) {
    def apply(t : (AnyRef, AnyRef)) : Boolean = {
        !(
            findInCategory(t._1, t._2) ||
            findInCategory(t._2, t._1))
    }

    private def findInCategory(a : AnyRef, b : AnyRef) : Boolean = {
        val next = category(a)
        a == b || (next != a && findInCategory(next, b))
    }
}
