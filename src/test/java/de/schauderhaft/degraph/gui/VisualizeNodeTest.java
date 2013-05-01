package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.schauderhaft.degraph.model.Node;

public class VisualizeNodeTest {

	private VisualizeNode vNode = null;
	private static Node node;
	private static Point DEFAULT_SIZE = new Point(200, 30);

	@BeforeClass
	public static void initNode() {
		/* should be the same for all tests */
		node = SampleGraph.getChessgraph().topNodes().iterator().next();

	}

	@Before
	public void init() {
		vNode = new VisualizeNode(DEFAULT_SIZE, node);
	}

	@Test
	public void vNodeShouldHaveSize() {
		Point size = vNode.getSize();
		assertNotNull(size);

	}

	public void sizeShouldBeDefault() {
		vNode.getSize();
	}

}
