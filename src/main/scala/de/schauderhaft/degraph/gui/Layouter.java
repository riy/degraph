package de.schauderhaft.degraph.gui;

import java.util.Set;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	private final JavaHierarchicGraph graph;

	private final Layout layout;

	public Layouter(JavaHierarchicGraph graph, Layout layout) {
		this.graph = graph;
		this.layout = layout;
	}

	/**
	 * Layout a set of nodes and returns a set of their controller.
	 */
	public Set<NodeController> layoutedNode(Set<Node> nodes) {
		return layout.getlayout(graph, nodes);
	}
}
