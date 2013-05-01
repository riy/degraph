package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.model.Node;

/**
 * Die Größe einer vNode ist abhängig vom seinem Inhalt. Es gibt eine
 * Default-Größe, die aber bei mehr als einer beinhaltenden Node überschritten
 * wird.
 * 
 * Je mehr Nodes diese Node enthält, desto größer ist die Darstellung!
 * 
 * 
 */
public class VisualizeNode {

	private final Node node;

	public VisualizeNode(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public NodeController createController() {
		NodeController nodeController = new NodeController(getNode());
		nodeController.setLayoutX(getX());
		nodeController.setLayoutY(getY());
		return nodeController;
	}

}
