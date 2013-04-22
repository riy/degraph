package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;

public class MainViewController {

	JavaHierarchicGraph graph;

	public MainViewController(JavaHierarchicGraph graph) {
		this.graph = graph;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private BorderPane topNodeContent;

	@FXML
	void openNode(MouseEvent event) {
	}

	@FXML
	void initialize() {
		boolean b = topNodeContent != null;
		assert b : "fx:id=\"topNodeContent\" was not injected: check your FXML file 'topNodeView.fxml'.";

	}

}
