package de.schauderhaft.degraph.analysis.asm

import de.schauderhaft.degraph.analysis.{NoSelfReference, AnalyzerLike}
import de.schauderhaft.degraph.model.Node
import de.schauderhaft.degraph.graph.Graph

object Analyzer extends AnalyzerLike {
  def analyze(sourceFolder: String, categorizer: (Node) => Node, filter: (Node) => Boolean): Graph = {
    val g = new Graph(categorizer, filter, new NoSelfReference(categorizer))
    g
  }
}
