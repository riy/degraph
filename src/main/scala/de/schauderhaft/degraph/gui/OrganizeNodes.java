package de.schauderhaft.degraph.gui;

import java.util.HashSet;
import java.util.Set;

import de.schauderhaft.degraph.model.Node;

public class OrganizeNodes {

	private final int nodeSize;
	private final int lineBreak;

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
		// START HACK - dont REVIEW, simple Layout!
		int x = 0;
		int y = 0;
		NodeSize nSize = new NodeSize(nodeSize, 40);
		for (Node node : nodes) {

			result.add(new VisualizeNode(nSize, new NodePosition(x, y), node,
					new HashSet<Node>()));

			if (x < lineBreak) {
				x += nodeSize + 40;
			} else {
				x = 0;
				y += nodeSize + 40;
			}
		}
		// END OF HACK
		return result;
	}

	public Set<VisualizeNode> getOrderedNodes(Set<Node> nodes) {

		return null;
	}
}
