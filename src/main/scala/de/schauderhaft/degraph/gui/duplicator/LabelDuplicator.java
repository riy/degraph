package de.schauderhaft.degraph.gui.duplicator;

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
		result.setStyle(source.getStyle());
		return result;
	}

}
