package de.schauderhaft.degraph.java;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

import de.schauderhaft.degraph.model.SimpleNode;

public class JavaApiTest {
	private SimpleNode node(String s) {
		return new SimpleNode(s, s);
	}

	@Test
	public void aNewGraphContainsNoTopNodes() throws Exception {
		JavaGraph graph = new JavaGraph();
		assertEquals(new HashSet<Object>(), graph.topNodes());
	}

	@Test
	public void aGraphContainsTheNodesThatGetAddedToTheGraph() {
		JavaGraph graph = new JavaGraph();
		SimpleNode node = new SimpleNode("", "");
		graph.add(node);
		assertTrue(graph.topNodes().contains(node));
	}

	@Test
	public void simpleNodesDontHaveConnections() {
		JavaGraph graph = new JavaGraph();
		SimpleNode node = new SimpleNode("", "");
		graph.add(node);

		assertEquals(new HashSet<>(), graph.connectionsOf(node));

	}

	@Test
	public void connectionsOfANodeAreTheNodesItHasEdgesWith() {
		JavaGraph graph = new JavaGraph();
		SimpleNode a = node("a");
		SimpleNode b = node("b");
		graph.connect(a, b);

		assertEquals(new HashSet<>(asList(b)), graph.connectionsOf(a));
	}

	@Test
	public void writeGraphMlFromGraphExample() {
		JavaGraph graph = new JavaGraph(new ChessCategory());
		graph.connect(node("King"), node("Queen"));
		graph.connect(node("Queen"), node("Rook"));
		graph.connect(node("Rook"), node("Bishop"));
		graph.connect(node("Rook"), node("Knight"));
		graph.connect(node("Knight"), node("Pawn"));
		graph.connect(node("Bishop"), node("Pawn"));

		graph.save("chess.graphml");
	}

	@Test
	public void aSimpleNodeHasNoContenten() {
		JavaGraph graph = new JavaGraph();
		SimpleNode node = new SimpleNode("", "");
		graph.add(node);
		assertEquals(new HashSet<>(), graph.contentsOf(node));
	}

	@Test
	public void categorizerGetsApplied() {
		JavaGraph graph = new JavaGraph(new ConstantCategorizer("x"));
		SimpleNode node = new SimpleNode("", "");
		graph.add(node);
		assertEquals(new HashSet<>(asList(node)), graph.contentsOf("x"));
	}
}
