package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.ChessCategory;
import de.schauderhaft.degraph.java.JavaGraph;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.SimpleNode;

/**
 * Collection of samples
 * 
 * 
 */
public class SampleGraph {

	private static SimpleNode node(String s) {
		return new SimpleNode(s, s);
	}

	public static JavaHierarchicGraph getChessgraph() {
		JavaGraph graph = new JavaGraph(new ChessCategory());
		graph.connect(node("King"), node("Queen"));
		graph.connect(node("Queen"), node("Rook"));
		graph.connect(node("Rook"), node("Bishop"));
		graph.connect(node("Rook"), node("Knight"));
		graph.connect(node("Knight"), node("Pawn"));
		graph.connect(node("Bishop"), node("Pawn"));

		graph.save("chess.graphml");
		return graph;
	}
}
