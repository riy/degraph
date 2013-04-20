package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

/**
 * Bekommt die eine Node zum Anzeigen!
 * 
 */
public class NodeController {

	Node node;
	JavaHierarchicGraph graph;

	public NodeController(Node node, JavaHierarchicGraph graph) {
		this.node = node;
		this.graph = graph;
	}

}
