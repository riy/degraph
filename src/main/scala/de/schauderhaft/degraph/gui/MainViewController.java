package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import de.schauderhaft.degraph.model.Node;

/**
 * Klasse noch nicht reviewen!
 * 
 * @author thomicha
 * 
 */
public class MainViewController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainView;

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {
		assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'MainView.fxml'.";

		Set<Node> topNodes = DataProvider.getTopNodes();
		assert topNodes != null : "no data";

	}
}
