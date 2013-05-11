package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	private int notYetImplementedX = 0;
	private int notYetImplementedY = 0;
	private final JavaHierarchicGraph graph;

	public Layouter(JavaHierarchicGraph graph) {
		this.graph = graph;
		notYetImplementedX = 0;
		notYetImplementedY = 0;

	}

	/**
	 * Returns Position for Node in this layout.
	 */
	public NodePosition nextPosition(Node childrenNode) {
		if (graph.topNodes().contains(childrenNode)) {
			notYetImplementedX = 0;
			notYetImplementedY += 320;
		}
		NodePosition result = new NodePosition(notYetImplementedX,
				notYetImplementedY);
		notYetImplementedX += 160;

		return result;
	}
}
