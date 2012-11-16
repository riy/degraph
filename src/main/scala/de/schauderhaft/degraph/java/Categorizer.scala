package de.schauderhaft.degraph.java

/**
 * A Categorizer groups nodes in categories. If a node does not belong to any category, the Categorizer should just return the node itself.
 *
 * Use Categorizer only when you use the library from Java (i.e. JavaGraph). If you use Scala just use a normal Function as parameter to Graph.
 *
 *  See JavaApiTest#writeGraphMlFromGraphExampl for example usage.
 */
trait Categorizer {
    def categoryOf(node: Object): Object
}