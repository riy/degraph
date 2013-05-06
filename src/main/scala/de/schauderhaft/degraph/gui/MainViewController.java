package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import de.schauderhaft.degraph.gui.util.FXMLUtil;
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
		FXMLUtil.loadAndSetController(this, "MainView.fxml");
	}

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println("KLickMainView");
	}

	@FXML
	void initialize() {

		int x = 0;
		Pane pane = new StackPane();

		Set<Node> topNodes = graph.topNodes();
		List<Node> childs = new ArrayList<>(graph.allNodes());

		Node parent = topNodes.iterator().next();

		Set<Node> children = new HashSet<>();
		children.add(topNodes.iterator().next());
		children.add(topNodes.iterator().next());
		VisualizeNode visualizeNode = new VisualizeNode(parent, children);
		NodeController createController = visualizeNode.createController();
		// createController.setWidthForAllPanes(visualizeNode.size());
		pane.getChildren().add(createController);
		System.out.println("VNode Size:" + visualizeNode.size());
		System.out.println("Controller :" + createController.toString());
		System.out.println("Pane: " + pane.getPrefWidth());

		AnchorPane childPane = (AnchorPane) createController
				.lookup(NodeController.PANE_NAME);

		VisualizeNode vn = new VisualizeNode(childs.get(0), new HashSet<Node>());

		NodeController c = vn.createController();
		c.setLayoutXForAllPanes(0);
		childPane.getChildren().add(c);

		//
		VisualizeNode vn1 = new VisualizeNode(childs.get(1),
				new HashSet<Node>());
		NodeController c2 = vn1.createController();
		childPane.getChildren().add(c2);
		c2.setLayoutXForAllPanes(140);

		VisualizeNode vn3 = new VisualizeNode(childs.get(2),
				new HashSet<Node>());
		NodeController c3 = vn3.createController();
		c3.setLayoutXForAllPanes(270);
		childPane.getChildren().add(c3);

		createController.resizeNodeContentView();

		// for (Node node : graph.topNodes()) {
		//
		// Set<Node> contentsOf = graph.contentsOf(node);
		//
		// VisualizeNode vNode = new VisualizeNode(node, contentsOf);
		//
		// NodeController controller = vNode.createController();
		// controller.setLayoutXForAllPanes(x);
		// // controller.setWidthForAllPanes(vNode.size());
		//
		// AnchorPane childPane = (AnchorPane) controller
		// .lookup(NodeController.PANE_NAME);
		//
		// int innerX = 0;
		// for (Node child : contentsOf) {
		// VisualizeNode childNOde = new VisualizeNode(child,
		// Collections.EMPTY_SET);
		// NodeController childController = childNOde.createController();
		// System.out.println(childNOde.getName() + " PrefWidth: "
		// + childController.getPrefWidth());
		// childController.setLayoutXForAllPanes(innerX);
		//
		// childPane.getChildren().add(childController);
		// System.out.println(childNOde.getName() + " PrefWidth: "
		// + childController.getPrefWidth());
		// innerX += childController.getPrefWidth() + 5;
		// System.out.println(childController);
		// }
		// controller.resizeNodeContentView();
		// System.out.println("Controller :" + controller.getPrefHeight());
		// System.out.println("PAne: " + pane.getPrefWidth());
		// pane.getChildren().add(controller);
		// }

		//
		// Set<Node> topNodes = graph.topNodes();
		// assert topNodes != null : "no data";
		//
		// organizeNodes(topNodes);
		// this.setPrefHeight(pane.getPrefHeight());
		// this.setPrefWidth(pane.getPrefWidth());
		this.setContent(pane);
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
