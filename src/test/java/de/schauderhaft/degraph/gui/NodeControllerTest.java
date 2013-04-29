package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class NodeControllerTest {

	private NodeController underTest = null;
	Node node;

	@Before
	public void init() {
		node = new SimpleNode("myType", "myName");
		underTest = new NodeController(node);
	}

	@Test
	public void shouldHaveChildren() {
		assertNotNull(underTest.getChildren());
	}

	@Test
	public void shouldHaveID() {
		javafx.scene.Node node = underTest.lookup(UiConst.NODE_NAME_LABEL);
		assertNotNull(node);
	}
}
