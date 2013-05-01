package de.schauderhaft.degraph.gui;

import java.awt.Point;
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
	 * VNodes doesn´t intersect each other, no olverlapping!
	 */
	public Set<VisualizeNode> getOrganizedNodes(Set<Node> nodes) {

		Set<VisualizeNode> result = new HashSet<>();
		if (nodes == null) {
			return result;
		}

		for (Node node : nodes) {
			result.add(new VisualizeNode(new Point(2, 3), new Point(10, 10),
					node, new HashSet<Node>()));
		}
		return result;
	}

}
