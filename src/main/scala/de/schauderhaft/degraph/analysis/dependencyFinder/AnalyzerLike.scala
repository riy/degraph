package de.schauderhaft.degraph.analysis.dependencyFinder

import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.model.Node

trait AnalyzerLike {
    def analyze(
        sourceFolder: String,
        categorizer: Node => Node,
        filter: Node => Boolean): Graph
}