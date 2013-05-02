package de.schauderhaft.degraph.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

	private final Map<String, Object> node4Controller = new HashMap<>();

	private final NodeLabelConverter converter = new NodeLabelConverter();

	private final JavaHierarchicGraph graph;

	public MainViewController(JavaHierarchicGraph graph) {
		this.graph = graph;
		loadingController();
	}

	private void loadingController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"MainView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {
		Set<Node> topNodes = graph.topNodes();
		assert topNodes != null : "no data";

		organizeNodes(topNodes);

	}

	private void organizeNodes(Set<Node> topNodes) {

		// TODO: make dynamic linebreak depends an sum of node
		final int LINEBREAK = 800;
		final int NODESPACE = 200;

		AnchorPane pane = new AnchorPane();

		Set<VisualizeNode> allNodes = new OrganizeNodes(NODESPACE, LINEBREAK)
				.getOrganizedNodes(topNodes);

		for (VisualizeNode vNode : allNodes) {

			NodeController nodeController = vNode.createController();

			addControllerToPane(pane, nodeController);

			organizeDependencies(vNode.getNode());

			node4Controller.put(converter.getNodeName(vNode.getNode()),
					nodeController);
		}
		addNodesPaneToScrollPane(pane);
	}

	private void organizeDependencies(Node node) {
		// TODO: have to be implemented
	}

	private void addNodesPaneToScrollPane(AnchorPane pane) {
		this.setContent(pane);
	}

	private void addControllerToPane(AnchorPane pane,
			NodeController nodeController) {
		pane.getChildren().add(nodeController);
	}

}
