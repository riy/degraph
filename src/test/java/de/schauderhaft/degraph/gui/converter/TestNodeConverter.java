package de.schauderhaft.degraph.gui.converter;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Label;

import org.junit.Test;

import de.schauderhaft.degraph.java.NodeBuilder;
import de.schauderhaft.degraph.model.Node;

public class TestNodeConverter {

	Map<Node, Node> categories = new HashMap<>();

	NodeLabelConverter underTest = new NodeLabelConverter();

	@Test
	public void shouldSameSize() {
		buildData();
		Set<Label> labels = underTest.toLabel(categories.keySet());
		assertEquals(categories.keySet().size(), labels.size());
	}

	public Node asNode(String s) {
		return NodeBuilder.create() //
				.name(s) //
				.typ("chesspiece") //
				.createSimpleNode();
	}

	private void buildData() {

		categories.put(asNode("Queen"), asNode("Heavy"));
		categories.put(asNode("Rook"), asNode("Heavy"));
		categories.put(asNode("Bishop"), asNode("Light"));
		categories.put(asNode("Knight"), asNode("Light"));
		categories.put(asNode("Light"), asNode("Figure"));
		categories.put(asNode("Heavy"), asNode("Figure"));
	}

}
