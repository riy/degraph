package de.schauderhaft.degraph.gui.duplicator;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;

import org.junit.Test;

public class PaneDuplicatorTest {

	private AnchorPane underTest = null;

	public void init() {
		// Properties from fxml
		underTest = new AnchorPane();
		underTest.setId("nodeTemplate");
		underTest.setLayoutX(93.0);
		underTest.setLayoutY(59.0);
		underTest.setMinHeight(80.0);
		underTest.setMinWidth(127.0);
		underTest.setPrefHeight(80.0);
		underTest.setPrefWidth(127.0);
		underTest.setStyle("node-panel");

		underTest.getChildren().add(new Label("testLabel"));
		underTest.getChildren().add(new AnchorPane());
		underTest.setEffect(new DropShadow());
	}

	@Test
	public void copyShouldHaveSameProperties() {

	}
}
