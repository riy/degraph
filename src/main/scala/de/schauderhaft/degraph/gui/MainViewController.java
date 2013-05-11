package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import de.schauderhaft.degraph.gui.util.FXMLUtil;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.Node;

/**
 * Controller for the main window which includes 0-n nodeViews
 * 
 */
public class MainViewController extends ScrollPane {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private final JavaHierarchicGraph graph;

	public MainViewController(JavaHierarchicGraph graph) {
		this.graph = graph;
		FXMLUtil.loadAndSetController(this, "MainView.fxml");
	}

	@FXML
	void onMouseClicked(MouseEvent event) {
		// TODO: not yet implemented
	}

	@FXML
	void initialize() {

		Pane pane = new StackPane();

		Set<Node> topNodes = new HashSet<>();
		ArrayList<Node> arrayList = new ArrayList<>(graph.topNodes());
		topNodes.add(arrayList.get(1));
		// topNodes.add(arrayList.get(2));

		Layouter layout = new Layouter(graph);
		for (Node parent : topNodes) {
			// Set<Node> childrenOfParent = graph.contentsOf(parent);
			Set<Node> childrenOfParent = graph.topNodes();
			// Set<Node> childrenOfParent = new HashSet<>();
			// ArrayList<Node> arrayList = new ArrayList<>(graph.topNodes());
			// childrenOfParent.add(arrayList.get(1));
			// childrenOfParent.add(arrayList.get(2));
			NodeController parentController = new NodeController(parent);

			AnchorPane parentContentPane = (AnchorPane) parentController
					.lookup(NodeController.PANE_NAME);
			for (Node childrenNode : childrenOfParent) {

				NodeController childController = new NodeController(
						childrenNode);
				parentContentPane.getChildren().add(childController);

				childController.setLayout(layout.nextPosition(childrenNode));
			}

			NodePosition positionForParent = layout.nextPosition(parent);
			System.out.println("Position: " + positionForParent);
			parentController.setLayout(positionForParent);
			pane.getChildren().add(parentController);
			parentController.fitToSize();
			System.out.println("position for Parent: " + parent.name() + " :"
					+ positionForParent + " Size: "
					+ parentController.getPrefHeight() + ","
					+ parentController.getPrefWidth());
		}

		this.setContent(pane);
	}
}
