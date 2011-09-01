package de.schauderhaft.dependencies.graph

/**
 * a special graph for gathering and organizing dependencies in a hirachical fashion
 */
class Graph {
	var topNodes : Set[AnyRef] = Set()
	def add(node : AnyRef) {topNodes = topNodes + node}
}