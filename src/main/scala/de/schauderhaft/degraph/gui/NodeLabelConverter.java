package de.schauderhaft.degraph.gui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Label;
import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.writer.Labeling;

public class NodeLabelConverter {

	public Set<Label> toLabel(Set<Node> nodes) {
		Set<Label> result = new HashSet<>();
		for (Node n : nodes) {
			String nodeName = getNodeName(n);
			result.add(new Label(nodeName));
		}
		return result;
	}

	public String getNodeName(Node n) {
		String nodeName = Labeling.apply(n, null);
		return nodeName;
	}
}
