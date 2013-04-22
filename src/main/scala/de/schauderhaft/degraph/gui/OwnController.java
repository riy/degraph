package de.schauderhaft.degraph.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class OwnController extends AnchorPane {

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

	public OwnController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"OwnNodeViewTemplate.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
