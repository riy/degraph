package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.model.Node;

public class OrganizeNodesTest {

	private OrganizeNodes underTest = null;
	private final int nodeSize = 200;
	private final int lineBreak = 800;

	@Before
	public void init() {
		underTest = new OrganizeNodes(nodeSize, lineBreak);
	}

	@Test
	public void getNotNull() {
		assertNotNull(underTest.getOrganizedNodes(null));
	}

	@Test
	public void shouldGetEmptyCollection() {
		Set<VisualizeNode> organizedNodes = underTest.getOrganizedNodes(null);
		assertTrue(organizedNodes.isEmpty());
	}

	@Test
	public void shouldBeSameSize() {
		Set<Node> nodes = getSampleData();
		Set<VisualizeNode> vNodes = underTest.getOrganizedNodes(nodes);
		assertEquals(nodes.size(), vNodes.size());
	}

	@Test
	public void shouldIncludeNode() {
		Set<Node> nodes = getSampleData();
		Set<VisualizeNode> vNodes = underTest.getOrganizedNodes(nodes);
		for (VisualizeNode vNode : vNodes) {
			assertTrue(nodes.contains(vNode.getNode()));
		}

	}

	@Test
	public void vNodesShouldntOverlapped() {
		Set<Node> nodes = getSampleData();
		Set<VisualizeNode> vNodes = underTest.getOrganizedNodes(nodes);
		for (VisualizeNode vNode : vNodes) {
			assertOverLappinng(vNode, new HashSet<VisualizeNode>(vNodes));
		}
	}

	private void assertOverLappinng(VisualizeNode vNode,
			HashSet<VisualizeNode> vNodes) {
		// dont check itself!
		vNodes.remove(vNode);

		for (VisualizeNode vn : vNodes) {
			// TODO: logic for test overlapping
		}

	}

	private Set<Node> getSampleData() {
		return SampleGraph.getChessgraph().topNodes();
	}
}
