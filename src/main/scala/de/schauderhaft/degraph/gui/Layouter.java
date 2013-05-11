package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	static int notYetImplementedX = 0;
	static int notYetImplementedY = 0;

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
		notYetImplementedX += 140;
		if (notYetImplementedX > 800) {
			notYetImplementedX = 0;
			notYetImplementedY += 140;
		}
		return result;
	}
}
