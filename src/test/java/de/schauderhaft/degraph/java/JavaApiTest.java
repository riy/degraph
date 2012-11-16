package de.schauderhaft.degraph.java;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

public class JavaApiTest {

	@Test
	public void aNewGraphContainsNoTopNodes() throws Exception {
		JavaGraph graph = new JavaGraph();
		assertEquals(new HashSet<Object>(), graph.topNodes());
	}

	@Test
	public void aGraphContainsTheNodesThatGetAddedToTheGraph() {
		JavaGraph graph = new JavaGraph();
		Object node = new Object();
		graph.add(node);
		assertTrue(graph.topNodes().contains(node));
	}

	@Test
	public void simpleNodesDontHaveConnections() {
		JavaGraph graph = new JavaGraph();
		Object node = new Object();
		graph.add(node);

		assertEquals(new HashSet<>(), graph.connectionsOf(node));

	}

	@Test
	public void connectionsOfANodeAreTheNodesItHasEdgesWith() {
		JavaGraph graph = new JavaGraph();
		Object a = new Object();
		Object b = new Object();
		graph.connect(a, b);

		assertEquals(new HashSet<>(asList(b)), graph.connectionsOf(a));
	}

	@Test
	public void writeGraphMlFromGraphExample() {
		JavaGraph graph = new JavaGraph(new ChessCategory());
		graph.connect("King", "Queen");
		graph.connect("Queen", "Rook");
		graph.connect("Rook", "Bishop");
		graph.connect("Rook", "Knight");
		graph.connect("Knight", "Pawn");
		graph.connect("Bishop", "Pawn");

		graph.save("chess.graphml");
	}

	@Test
	public void aSimpleNodeHasNoContenten() {
		JavaGraph graph = new JavaGraph();
		Object node = new Object();
		graph.add(node);
		assertEquals(new HashSet<>(), graph.contentsOf(node));
	}

	@Test
	public void categorizerGetsApplied() {
		JavaGraph graph = new JavaGraph(new ConstantCategorizer("x"));
		Object node = new Object();
		graph.add(node);
		assertEquals(new HashSet<>(asList(node)), graph.contentsOf("x"));
	}
}
