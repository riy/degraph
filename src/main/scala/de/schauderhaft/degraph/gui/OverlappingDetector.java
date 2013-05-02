package de.schauderhaft.degraph.gui;

/**
 * This class checks overlapping between two Visualize Nodes.
 */
public class OverlappingDetector {

	/**
	 * checks if this node touches another node.
	 */
	public boolean overlapping(VisualizeNode that, VisualizeNode me) {
		if (isLeftOf(me, that))
			return false;
		if (isLeftOf(that, me))
			return false;
		if (isAbove(that, me))
			return false;
		if (isAbove(me, that))
			return false;
		return true;

	}

	private boolean isAbove(VisualizeNode that, VisualizeNode me) {
		return me.getY() + me.size().y < that.getY();
	}

	private boolean isLeftOf(VisualizeNode that, VisualizeNode me) {
		return me.getX() + me.size().x < that.getX();
	}
}
