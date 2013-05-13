package de.schauderhaft.degraph.gui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.layout.AnchorPane;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

public class Layouter {

	private final JavaHierarchicGraph graph;

	public Layouter(JavaHierarchicGraph graph) {
		this.graph = graph;

	}

	/**
	 * Layout a set of nodes and returns a set of their controller.
	 */
	public Set<NodeController> layoutedChildren(Set<Node> nodes) {
		Set<NodeController> result = new HashSet<>();
		int y = 0;
		for (Node node : nodes) {
			Set<Node> childrenOfParent = graph.topNodes(); // testcode TODO:
			// delete this
			// Set<Node> childrenOfParent = graph.contentsOf(node);

			NodeController parentController = new NodeController(node);
			AnchorPane parentContentPane = (AnchorPane) parentController
					.lookup(NodeController.PANE_NAME);

			int x = 0;
			for (Node childrenNode : childrenOfParent) {

				NodeController childController = new NodeController(
						childrenNode);
				parentContentPane.getChildren().add(childController);

				childController.setLayout(new NodePosition(x, 10));
				x += childController.getPrefWidth() + 10;
			}

			parentController.setLayout(new NodePosition(0, y));
			parentController.fitToSize();
			y += parentController.getPrefHeight() + 10;
			System.out.println("y:" + y);
			result.add(parentController);
		}
		return result;
	}
}
