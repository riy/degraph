package de.schauderhaft.degraph.gui;

import java.util.HashSet;
import java.util.Set;

import de.schauderhaft.degraph.model.Node;

public class OrganizeNodes {

	int nodeSize;
	int lineBreak;

	public OrganizeNodes(int nodeSize, int lineBreak) {
		this.nodeSize = nodeSize;
		this.lineBreak = lineBreak;
	}

	/**
	 * Convert a set of nodes to visualize nodes (vNode).
	 * 
	 * VNodes doesn´t intersect each other
	 */
	public Set<VisualizeNode> getOrganizedNodes(Set<Node> nodes) {

		Set<VisualizeNode> result = new HashSet<>();
		if (nodes == null) {
			return result;
		}

		for (Node node : nodes) {
			result.add(new VisualizeNode(node));
		}
		return result;
	}

}
