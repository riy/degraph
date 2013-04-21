package de.schauderhaft.degraph.gui;

import javafx.scene.control.Label;

/**
 * Copy Properties from an Label to a new Label
 * 
 * @author thomicha
 * 
 */
public class LabelDuplicator {

	public Label duplicate(Label source) {
		Label result = new Label(source.getText());

		return result;
	}

}
