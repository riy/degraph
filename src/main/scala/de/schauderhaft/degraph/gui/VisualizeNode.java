package de.schauderhaft.degraph.gui;

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
	private NodeSize defaultSize;
	private NodePosition position;
	private final Set<Node> children;

	public VisualizeNode(NodeSize size, NodePosition position, Node node,
			Set<Node> children) {
		this.node = node;
		this.defaultSize = size;
		this.position = position;
		this.children = children;

	}

	public VisualizeNode(Node node, Set<Node> children) {
		this.node = node;
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

		// nodeController.setLayoutX(getX());
		// nodeController.setLayoutY(getY());
		return nodeController;
	}

	public NodeSize size() {
		// TODO: first try, make it flat in x-direction!
		if (children.isEmpty()) {
			return new NodeSize(100, 100);
		}
		return new NodeSize(100 * childrenSize(), 100);

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

	public String getName() {
		NodeLabelConverter c = new NodeLabelConverter();
		return c.getNodeName(node);
	}

}
