package de.schauderhaft.degraph.analysis.dependencyFinder

import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node

/**
 * trait abstracting over who or what provides dependencies.
 *
 *  Usage of this trait instead of it single current implementation
 *  allows the implementation later to be replaced by something else,
 *  like analyzers providing Spring or database dependencies.
 */
trait AnalyzerLike {
    def analyze(
        sourceFolder: String,
        categorizer: Node => Node,
        filter: Node => Boolean): Graph
}