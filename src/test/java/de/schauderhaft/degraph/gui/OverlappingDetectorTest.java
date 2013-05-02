package de.schauderhaft.degraph.gui;

import java.util.HashSet;

import org.junit.Test;

import de.schauderhaft.degraph.model.Node;

public class OverlappingDetectorTest {

	NodeSize defaultSize = new NodeSize(10, 10);
	NodePosition me = new NodePosition(50, 50);

	NodePosition left = new NodePosition(20, 50);
	NodePosition right = new NodePosition(80, 50);
	NodePosition up = new NodePosition(50, 20);
	NodePosition down = new NodePosition(50, 80);

	OverlappingDetector underTest = new OverlappingDetector();

	@Test
	public void shouldBeLeftOfMe() {
		underTest.overlapping(getNode(left), getNode(me));
	}

	private VisualizeNode getNode(NodePosition pos) {
		return new VisualizeNode(defaultSize, pos, null, new HashSet<Node>());
	}
}
