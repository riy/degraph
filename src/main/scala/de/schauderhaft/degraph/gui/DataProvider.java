package de.schauderhaft.degraph.gui;

import java.util.Set;

import javafx.scene.control.Label;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

/**
 * Holds the {@link JavaHierarchicGraph} - graph for direct access.
 * 
 */
public class DataProvider {

	private JavaHierarchicGraph graph;

	private static DataProvider singelton = new DataProvider();

	public static DataProvider getInstance() {
		return singelton;
	}

	public void setData(JavaHierarchicGraph g) {
		graph = g;
	}

	public Set<Node> getTopNodes() {
		return graph.topNodes();
	}

	public Set<Node> getNodes() {
		return graph.allNodes();
	}

	public Label getNodeName(Set<Node> nodes) {
		NodeLabelConverter converter = new NodeLabelConverter();
		return converter.toLabel(nodes).iterator().next();
	}
}
