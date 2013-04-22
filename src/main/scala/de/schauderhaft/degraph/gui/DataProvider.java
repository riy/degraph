package de.schauderhaft.degraph.gui;

import java.util.Set;

import javafx.scene.control.Label;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

/**
 * Holds the {@link JavaHierarchicGraph} - graph for direct access.
 * 
 * @author thomicha
 * 
 */
public class DataProvider {

	private static JavaHierarchicGraph GRAPH;

	public static void setData(JavaHierarchicGraph g) {
		GRAPH = g;

	}

	public static Set<Node> getTopNodes() {
		return GRAPH.topNodes();
	}

	public static Label getNodeName(Set<Node> nodes) {
		NodeLabelConverter converter = new NodeLabelConverter();
		return converter.toLabel(nodes).iterator().next();
	}
}
