package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
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
	private ScrollPane scrollPane;

	private final Map<String, Object> node4Controller = new HashMap<>();

	private final Logger LOG = Logger.getLogger(MainViewController.class);

	private final NodeLabelConverter converter = new NodeLabelConverter();

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {
		assert scrollPane != null : "fx:id=\"mainView\" was not injected: check your FXML file 'MainView.fxml'.";

		Set<Node> topNodes = DataProvider.getInstance().getTopNodes();
		assert topNodes != null : "no data";

		organizeNodes(topNodes);

	}

	private void organizeNodes(Set<Node> topNodes) {

		// TODO: make dynamic linebreak depends an sum of node
		final int LINEBREAK = 800;
		final int NODESPACE = 200;
		int placeX = 0;
		int placeY = 30;
		AnchorPane pane = new AnchorPane();
		
		for (Node node : topNodes) {

			NodeController nodeController = createController(placeX, placeY,
					node);

			addControllerToPane(pane, nodeController);

			placeX += NODESPACE;
			if (placeX > LINEBREAK) {
				placeX = 0;
				placeY = +NODESPACE;
			}
			
			node4Controller.put(converter.getNodeName(node), nodeController);
		}
		addNodesPaneToScrollPane(pane);
	}

	private void addNodesPaneToScrollPane(AnchorPane pane) {
		scrollPane.setContent(pane);
	}

	private void addControllerToPane(AnchorPane pane,
			NodeController nodeController) {
		pane.getChildren().add(nodeController);
	}

	private NodeController createController(int placeX, int placeY, Node node) {
		NodeController nodeController = new NodeController(node);
		nodeController.setLayoutX(placeX);
		nodeController.setLayoutY(placeY);
		return nodeController;
	}
}
