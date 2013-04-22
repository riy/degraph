package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;

import de.schauderhaft.degraph.model.Node;

/**
 * Controller for the main window which includes 0-n nodeViews
 * 
 */
public class MainViewController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane mainView;

	private final Map<String, Object> node4Controller = new HashMap<>();

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

		NodeController own = new NodeController();
		mainView.getChildren().add(own);

		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(this.location);
		//
		// // for (Node n : topNodes) {
		// for (int i = 0; i < 10; i++) {
		// try {
		// Parent node = (Parent) loader.load(getClass().getResource(
		// "NodeViewTemplate.fxml").openStream());
		//
		// Object controller = loader.getController();
		// assert controller != null;
		// NodeLabelConverter converter = new NodeLabelConverter();
		// // String nodeName = converter.getNodeName(n);
		// // node4Controller.put(nodeName, controller);
		// mainView.getChildren().add(node);
		// } catch (IOException e) {
		// LOG.error(e);
		// }
		// }

	}
}
