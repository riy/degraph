package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import javafx.scene.control.Label;

import org.junit.Test;

public class LabelDuplicatorTest {

	private final LabelDuplicator underTest = new LabelDuplicator();

	@Test
	public void testName() {
		Label source = new Label("myName");
		Label dest = underTest.duplicate(source);
		assertEquals(source.getText(), dest.getText());
	}
}
