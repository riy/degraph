package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;

import de.schauderhaft.degraph.model.Node;
import de.schauderhaft.degraph.model.SimpleNode;

public class NodeControllerTest {

	private static final String MY_NAME = "myName";
	private NodeController underTest = null;
	Node node;

	@Before
	public void init() {
		node = new SimpleNode("myType", MY_NAME);
		underTest = new NodeController(node);
	}

	@Test
	public void shouldHaveChildren() {
		assertNotNull(underTest.getChildren());
	}

	@Test
	public void shouldHaveID() {
		javafx.scene.Node node = underTest.lookup(NodeController.LABEL_NAME);
		assertNotNull(node);
	}

	@Test
	public void shouldBeALabelAtID() {
		javafx.scene.Node node = underTest.lookup(NodeController.LABEL_NAME);
		assertTrue(node instanceof Label);
	}

	@Test
	public void labelSHouldHaveSameName() {
		Label label = (Label) underTest.lookup(NodeController.LABEL_NAME);
		assertEquals(MY_NAME, label.getText());
	}
}
