package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class NodeController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane nodeViewTemplate;

	@FXML
	void initialize() {
		assert nodeViewTemplate != null : "fx:id=\"nodeViewTemplate\" was not injected: check your FXML file 'NodeViewTemplate.fxml'.";

	}

}
