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

	private final Logger LOG = Logger.getLogger(MainViewController.class);

	private final NodeLabelConverter converter = new NodeLabelConverter();

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {
		assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'MainView.fxml'.";

		Set<Node> topNodes = DataProvider.getInstance().getTopNodes();
		assert topNodes != null : "no data";

		organizeNodes(topNodes);

	}

	private void organizeNodes(Set<Node> topNodes) {

		// TODO: only for tests
		// this ist not the solution for a real View !!
		int placeX = 0;
		int placeY = 0;
		for (Node node : topNodes) {

			NodeController nodeController = new NodeController(node);
			nodeController.setLayoutX(placeX);
			nodeController.setLayoutY(placeY);
			placeX += 150;
			mainView.getChildren().add(nodeController);

			node4Controller.put(converter.getNodeName(node), nodeController);
			if (placeX > 500) {
				placeX = 0;
				placeY = +100;
			}
		}
	}
}
