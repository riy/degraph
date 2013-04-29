package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

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
	public void contentShouldBeAnchorPane() {
		Node content = underTest.getContent();
		assertEquals(AnchorPane.class, content.getClass());
	}

	@Test
	public void contentShouldHaveChildren() {
		AnchorPane anchorPane = (AnchorPane) underTest.getContent();
		assertTrue(anchorPane.getChildren() != null);
	}

	@Test
	public void contentChildrenSHouldNotBeEmpty() {
		AnchorPane anchorPane = (AnchorPane) underTest.getContent();
		assertFalse(anchorPane.getChildren().isEmpty());
	}

	@Test
	public void contentShouldHaveSixChildren() {
		AnchorPane anchorPane = (AnchorPane) underTest.getContent();
		assertEquals(6, anchorPane.getChildren().size());
	}
}
