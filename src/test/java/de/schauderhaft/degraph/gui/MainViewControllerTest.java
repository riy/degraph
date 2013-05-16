package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;

/**
 * Default - Graph for tests is the chess-graph
 * 
 */
public class MainViewControllerTest {

	private MainViewController underTest;
	private final JavaHierarchicGraph graph = SampleGraph.getChessgraph();

	@Before
	public void init() {
		underTest = new MainViewController(getGraph());
	}

	private JavaHierarchicGraph getGraph() {
		return graph;
	}

	@Test
	public void paneShouldHaveContent() {
		assertNotNull(underTest.getContent());
	}

	@Test
	public void contentShouldHaveChildren() {
		Pane anchorPane = (Pane) underTest.getContent();
		assertTrue(anchorPane.getChildren() != null);
	}

	@Test
	public void contentChildrenSHouldNotBeEmpty() {
		Pane anchorPane = (Pane) underTest.getContent();
		assertFalse(anchorPane.getChildren().isEmpty());
	}

	@Test
	public void contentShouldHaveSixChildren() {
		Pane anchorPane = (Pane) underTest.getContent();
		assertEquals(6, anchorPane.getChildren().size());
	}

	@Test
	public void childShouldBeNodeController() {
		ObservableList<Node> children = ((Pane) underTest.getContent())
				.getChildren();
		Node node = children.get(0);
		assertEquals(NodeController.class, node.getClass());
	}
}
