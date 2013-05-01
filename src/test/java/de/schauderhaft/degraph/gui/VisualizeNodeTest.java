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

	@BeforeClass
	public static void initNode() {
		/* should be the same for all tests */
		node = SampleGraph.getChessgraph().topNodes().iterator().next();

	}

	@Before
	public void init() {
		vNode = new VisualizeNode(node);
	}

	@Test
	public void vNodeShouldHaveSize() {
		Point size = vNode.getSize();
		assertNotNull(size);

	}
}
