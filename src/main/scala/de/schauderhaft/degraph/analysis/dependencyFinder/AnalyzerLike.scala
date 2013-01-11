package de.schauderhaft.degraph.analysis.dependencyFinder

import de.schauderhaft.degraph.graph.Graph

trait AnalyzerLike {
    def analyze(
        sourceFolder: String,
        categorizer: AnyRef => AnyRef,
        filter: AnyRef => Boolean): Graph
}