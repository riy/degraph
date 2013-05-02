package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		assertFalse(underTest.overlapping(getNode(left), getNode(me)));
	}

	@Test
	public void shouldBeRightofMe() {
		assertFalse(underTest.overlapping(getNode(right), getNode(me)));
	}

	@Test
	public void shouldBeUpperMe() {
		assertFalse(underTest.overlapping(getNode(up), getNode(me)));

	}

	@Test
	public void shouldBeDownOfMe() {
		assertFalse(underTest.overlapping(getNode(down), getNode(me)));
	}

	@Test
	public void shouldBeOverlappedRight() {
		NodePosition right = new NodePosition(59, 50);
		assertTrue(underTest.overlapping(getNode(right), getNode(me)));

	}

	@Test
	public void shouldBeOverlappedLeft() {
		NodePosition left = new NodePosition(41, 50);
		assertTrue(underTest.overlapping(getNode(left), getNode(me)));

	}

	@Test
	public void shouldBeOverlappedUpper() {
		NodePosition up = new NodePosition(50, 59);
		assertTrue(underTest.overlapping(getNode(up), getNode(me)));

	}

	@Test
	public void shouldBeOverlappedDown() {
		NodePosition down = new NodePosition(50, 59);
		assertTrue(underTest.overlapping(getNode(down), getNode(me)));

	}

	@Test
	public void testLimesXDirection() {
		// right
		assertFalse(underTest.overlapping(getNode(new NodePosition(39, 50)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(40, 50)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(41, 50)),
				getNode(me)));
		// left
		assertTrue(underTest.overlapping(getNode(new NodePosition(59, 50)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(60, 50)),
				getNode(me)));
		assertFalse(underTest.overlapping(getNode(new NodePosition(61, 50)),
				getNode(me)));
	}

	@Test
	public void testLimesYDirection() {
		// down
		assertFalse(underTest.overlapping(getNode(new NodePosition(50, 39)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(50, 40)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(50, 41)),
				getNode(me)));
		// up
		assertTrue(underTest.overlapping(getNode(new NodePosition(50, 59)),
				getNode(me)));
		assertTrue(underTest.overlapping(getNode(new NodePosition(50, 60)),
				getNode(me)));
		assertFalse(underTest.overlapping(getNode(new NodePosition(50, 61)),
				getNode(me)));
	}

	private VisualizeNode getNode(NodePosition pos) {
		return new VisualizeNode(defaultSize, pos, null, new HashSet<Node>());
	}
}
