package de.schauderhaft.degraph.gui;

import java.util.Set;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	private final JavaHierarchicGraph graph;

	enum LayoutType {
		FLOWLAYOUT
	};

	LayoutType type;

	public Layouter(JavaHierarchicGraph graph, LayoutType type) {
		this.graph = graph;
		this.type = type;
	}

	/**
	 * Layout a set of nodes and returns a set of their controller.
	 */
	public Set<NodeController> layoutedNode(Set<Node> nodes) {
		switch (type) {
		case FLOWLAYOUT:
			return new RowLayout().getlayout(graph, nodes);

		default:
			return new RowLayout().getlayout(graph, nodes);
		}
	}
}
