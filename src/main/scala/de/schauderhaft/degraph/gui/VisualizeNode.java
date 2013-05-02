package de.schauderhaft.degraph.gui;

import java.awt.Point;
import java.util.Set;

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
	private final NodeSize defaultSize;
	private final NodePosition position;
	private final Set<Node> children;

	public VisualizeNode(NodeSize size, NodePosition position, Node node,
			Set<Node> children) {
		this.node = node;
		this.defaultSize = size;
		this.position = position;
		this.children = children;

	}

	public Node getNode() {
		return node;
	}

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	public NodeController createController() {
		NodeController nodeController = new NodeController(getNode());
		nodeController.setLayoutX(getX());
		nodeController.setLayoutY(getY());
		return nodeController;
	}

	public Point size() {
		// TODO: first try, make it flat in x-direction!
		if (children.isEmpty()) {
			return defaultSize;
		}
		return new Point(defaultSize.x * childrenSize(), defaultSize.y);

	}

	public int childrenSize() {
		return children.size();
	}

	/**
	 * checks if this node touches another node.
	 */
	public boolean overlapped(VisualizeNode that) {
		return new OverlappingDetector().overlapping(that, this);

	}

}
