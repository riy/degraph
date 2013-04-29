package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.java.JavaHierarchicGraph;

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
	public void paneShouldHaveChilds() {
		assertNotNull(underTest.getChildrenUnmodifiable());
	}
}
