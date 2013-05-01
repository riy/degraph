package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.schauderhaft.degraph.model.Node;

public class VisualizeNodeTest {

	private VisualizeNode vNode = null;
	private static Node node;
	private static Point DEFAULT_SIZE = new Point(200, 30);
	private static Point SAMPLE_POSITION = new Point(22, 33);

	@BeforeClass
	public static void initNode() {
		/* should be the same for all tests */
		for (Node n : SampleGraph.getChessgraph().topNodes()) {
			if (n.name().startsWith("Queen")) {
				node = n;
				break;
			}
		}

	}

	@Before
	public void init() {
		vNode = new VisualizeNode(DEFAULT_SIZE, SAMPLE_POSITION, node,
				SampleGraph.getChessgraph().contentsOf(node));
	}

	@Test
	public void vNodeShouldHaveSize() {
		Point size = vNode.size();
		assertNotNull(size);

	}

	@Test
	public void sizeShouldBeDefault() {
		vNode.size();
	}

	@Test
	public void posxShouldbe3() {
		int posX = 3;
		vNode = new VisualizeNode(DEFAULT_SIZE, new Point(posX, 1), node,
				new HashSet<Node>());
		assertEquals(posX, vNode.getX());
	}

	@Test
	public void posyShouldBe1() {
		int posY = 1;
		vNode = new VisualizeNode(DEFAULT_SIZE, new Point(3, posY), node,
				new HashSet<Node>());
		assertEquals(posY, vNode.getY());

	}

	@Test
	public void childrenSizeShouldBe5() {
		Set<Node> sampleChildren = SampleGraph.getChessgraph().topNodes();
		vNode = new VisualizeNode(DEFAULT_SIZE, SAMPLE_POSITION, node,
				sampleChildren);
		assertEquals(sampleChildren.size(), vNode.childrenSize());
	}

	@Test
	public void expectGreaterSizeAsDefault() {
		Set<Node> sampleChildren = SampleGraph.getChessgraph().topNodes();
		vNode = new VisualizeNode(DEFAULT_SIZE, SAMPLE_POSITION, node,
				sampleChildren);
		assertTrue(area(DEFAULT_SIZE) < area(vNode.size()));
	}

	private double area(Point p) {
		return p.x * p.y;
	}
}
