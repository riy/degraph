package de.schauderhaft.degraph.java;

import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class NodeBuilder {

	private String name;
	private String typ;

	private NodeBuilder() {

	}

	public static NodeBuilder create() {
		return new NodeBuilder();
	}

	public NodeBuilder name(String name) {
		this.name = name;
		return this;
	}

	public NodeBuilder typ(String typ) {
		this.typ = typ;
		return this;
	}

	public Node createSimpleNode() {
		Node simpleNode = new SimpleNode(typ, name);
		return simpleNode;
	}

}
