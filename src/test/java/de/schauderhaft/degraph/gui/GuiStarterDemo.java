package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.ChessCategory;
import de.schauderhaft.degraph.java.JavaGraph;
import de.schauderhaft.degraph.model.SimpleNode;

public class GuiStarterDemo {

	/**
	 * Start gui with chessexample
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GuiStarter s = new GuiStarter();

		JavaGraph graph = new JavaGraph(new ChessCategory());
		graph.connect(node("King"), node("Queen"));
		graph.connect(node("Queen"), node("Rook"));
		graph.connect(node("Rook"), node("Bishop"));
		graph.connect(node("Rook"), node("Knight"));
		graph.connect(node("Knight"), node("Pawn"));
		graph.connect(node("Bishop"), node("Pawn"));

		graph.save("chess.graphml");
		s.show(graph);
	}

	private static SimpleNode node(String s) {
		return new SimpleNode(s, s);
	}
}
