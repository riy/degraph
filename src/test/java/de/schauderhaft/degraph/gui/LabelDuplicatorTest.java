package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertEquals;
import javafx.scene.control.Label;

import org.junit.Before;
import org.junit.Test;

public class LabelDuplicatorTest {

	private final LabelDuplicator underTest = new LabelDuplicator();

	private Label source = null;

	@Before
	public void init() {
		// values copied from fxml-file
		source = new Label("nodeName");
		source.setPrefWidth(201.0);
		source.setStyle("@degrpahStyle.css");

	}

	@Test
	public void nameShouldBeCopied() {
		Label dest = underTest.duplicate(source);
		assertEquals(source.getText(), dest.getText());
	}

	@Test
	public void cssStyleShouldBeCopied() {
		Label dest = underTest.duplicate(source);
		assertEquals(source.getStyle(), dest.getStyle());
	}
}
