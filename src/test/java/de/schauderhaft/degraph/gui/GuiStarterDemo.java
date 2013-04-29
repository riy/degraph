package de.schauderhaft.degraph.gui;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;

public class GuiStarterDemo {

	/**
	 * Start gui with chessexample
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GuiStarter s = new GuiStarter();

		JavaHierarchicGraph graph = SampleGraph.getChessgraph();
		s.show(graph);
	}

}
