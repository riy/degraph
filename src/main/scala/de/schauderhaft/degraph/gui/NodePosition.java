package de.schauderhaft.degraph.gui;

import java.awt.Point;

public class NodePosition extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodePosition(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
