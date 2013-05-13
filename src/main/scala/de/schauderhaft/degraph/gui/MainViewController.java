package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import de.schauderhaft.degraph.gui.util.FXMLUtil;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;

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

		// pane as content for this scrollPane
		AnchorPane pane = new AnchorPane();

		Layouter layout = new Layouter(graph);
		pane.getChildren().addAll(layout.layoutedNode(graph.topNodes()));

		this.setContent(pane);
	}
}
