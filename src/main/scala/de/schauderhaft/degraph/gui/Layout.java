package de.schauderhaft.degraph.gui;

import java.util.Set;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public interface Layout {

	/**
	 * Returns for every node the nodecontroller, which is layouted.
	 */
	public Set<NodeController> getlayout(JavaHierarchicGraph graph,
			Set<Node> nodes);
}
