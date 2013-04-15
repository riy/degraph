package de.schauderhaft.degraph.gui.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Label;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.java.NodeBuilder;
import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class TestNodeConverter {

	Map<Node, Node> categories = new HashMap<>();

	NodeLabelConverter underTest = new NodeLabelConverter();

	@Before
	public void initTestData() {
		buildData();
	}

	@After
	public void clear() {
		categories.clear();
	}

	@Test
	public void shouldSameSize() {
		Set<Label> labels = underTest.toLabel(categories.keySet());
		assertEquals(categories.keySet().size(), labels.size());
	}

	private Node asNode(String s) {
		return NodeBuilder.create() //
				.name(s) //
				.typ("chesspiece") //
				.createSimpleNode();
	}

	@Test
	public void testSameContent() {
		Set<Label> labels = underTest.toLabel(categories.keySet());
		for (Label l : labels) {
			boolean exists = false;
			for (Node n : categories.keySet()) {
				if (n instanceof SimpleNode) {
					SimpleNode simpleNode = (SimpleNode) n;
					// System.out
					// .println(l.getText() + " <> " + simpleNode.name());
					if (l.getText().equals(simpleNode.name())) {
						// System.out.println(l.getText() + " == "
						// + simpleNode.name());
						exists = true;
						break;
					}
				}
			}
			assertTrue("check: " + l.getText(), exists);
		}
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
