package de.schauderhaft.degraph.gui.converter;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Label;
import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class NodeLabelConverter {

	public Set<Label> toLabel(Set<Node> nodes) {
		Set<Label> result = new HashSet<>();
		for (Node n : nodes) {
			if (n instanceof SimpleNode) {
				SimpleNode simpleNode = (SimpleNode) n;
				result.add(new Label(simpleNode.name()));
			} else {
				result.add(new Label(n.toString()));
			}
		}

		return result;
	}

}
