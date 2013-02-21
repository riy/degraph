package de.schauderhaft.degraph.java;

import java.util.HashMap;
import java.util.Map;

import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class ChessCategory implements Categorizer {
	public SimpleNode asNode(String s) {
		return new SimpleNode("chesspiece", s);
	}

	private final Map<SimpleNode, SimpleNode> categories = new HashMap<>();

	{
		categories.put(asNode("Queen"), asNode("Heavy"));
		categories.put(asNode("Rook"), asNode("Heavy"));
		categories.put(asNode("Bishop"), asNode("Light"));
		categories.put(asNode("Knight"), asNode("Light"));
		categories.put(asNode("Light"), asNode("Figure"));
		categories.put(asNode("Heavy"), asNode("Figure"));
	}

	@Override
	public Node categoryOf(Object node) {
		Node result = categories.get(node);
		if (result == null)
			return (Node) node;
		else
			return result;
	}

}
