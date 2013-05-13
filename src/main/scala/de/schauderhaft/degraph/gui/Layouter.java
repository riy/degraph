package de.schauderhaft.degraph.gui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.layout.AnchorPane;
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

	/**
	 * Layout a set of nodes and returns a set of their controller.
	 */
	public Set<NodeController> layoutedChildren(Set<Node> nodes) {
		Set<NodeController> result = new HashSet<>();
		for (Node node : nodes) {
			// Set<Node> childrenOfParent = graph.topNodes(); // testcode TODO:
			// delete thie
			Set<Node> childrenOfParent = graph.contentsOf(node);

			NodeController parentController = new NodeController(node);

			AnchorPane parentContentPane = (AnchorPane) parentController
					.lookup(NodeController.PANE_NAME);
			for (Node childrenNode : childrenOfParent) {

				NodeController childController = new NodeController(
						childrenNode);
				parentContentPane.getChildren().add(childController);

				childController.setLayout(nextPosition(childrenNode));
			}

			NodePosition positionForParent = nextPosition(node);
			System.out.println("Position: " + positionForParent);
			parentController.setLayout(positionForParent);
			parentController.fitToSize();
			System.out.println("position for Parent: " + node.name() + " :"
					+ positionForParent + " Size: "
					+ parentController.getPrefHeight() + ","
					+ parentController.getPrefWidth());

			result.add(parentController);
		}
		return result;
	}
}
