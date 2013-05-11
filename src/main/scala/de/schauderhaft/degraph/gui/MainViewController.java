package de.schauderhaft.degraph.gui;

import java.net.URL;
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
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {

		Pane pane = new StackPane();

		Set<Node> topNodes = graph.topNodes();
		Layouter layouter = new Layouter(graph);
		for (Node parent : topNodes) {
			Set<Node> childrenOfParent = graph.contentsOf(parent);
			// Set<Node> childrenOfParent = graph.topNodes();

			NodeController parentController = new NodeController(parent);

			AnchorPane parentContentPane = (AnchorPane) parentController
					.lookup(NodeController.PANE_NAME);
			for (Node childrenNode : childrenOfParent) {

				NodeController childController = new NodeController(
						childrenNode);
				parentContentPane.getChildren().add(childController);

				childController.setLayout(layouter.nextPosition(childrenNode));
			}
			parentController.setLayout(layouter.nextPosition(parent));
			pane.getChildren().add(parentController);
			parentController.fitToSize();
		}

		this.setContent(pane);
	}
}
