package de.schauderhaft.degraph.analysis.dependencyFinder

import de.schauderhaft.degraph.graph.Graph
import de.schauderhaft.degraph.model.Node

trait AnalyzerLike {
    def analyze(
        sourceFolder: String,
        categorizer: AnyRef => Node,
        filter: AnyRef => Boolean): Graph
}