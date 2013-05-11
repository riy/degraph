package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	private int notYetImplementedX = 0;
	private int notYetImplementedY = 0;

	public Layouter(JavaHierarchicGraph graph) {
		notYetImplementedX = 0;
		notYetImplementedY = 0;

	}

	/**
	 * Returns Position for Node in this layout.
	 */
	public NodePosition nextPosition(Node childrenNode) {
		NodePosition result = new NodePosition(notYetImplementedX,
				notYetImplementedY);
		notYetImplementedX += 160;
		if (notYetImplementedX > 800) {
			notYetImplementedX = 0;
			notYetImplementedY += 160;
		}
		return result;
	}
}
