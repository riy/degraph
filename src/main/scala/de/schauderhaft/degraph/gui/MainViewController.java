package de.schauderhaft.degraph.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;

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

	Logger LOG = Logger.getLogger(MainViewController.class);

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {
		assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'MainView.fxml'.";

		Set<Node> topNodes = DataProvider.getInstance().getTopNodes();
		assert topNodes != null : "no data";

		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(this.location);
			Parent node = (Parent) loader.load(getClass().getResource(
					"NodeViewTemplate.fxml").openStream());

			Object controller = loader.getController();
			assert controller != null;

			mainView.getChildren().add(node);
		} catch (IOException e) {
			LOG.error(e);
		}

	}
}
