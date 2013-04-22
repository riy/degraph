package de.schauderhaft.degraph.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import de.schauderhaft.degraph.model.Node;

public class NodeController extends AnchorPane {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane nodeView;

	private final Node node;

	private final NodeLabelConverter converter = new NodeLabelConverter();

	@FXML
	void initialize() {

		Label label = (Label) this.lookup("#nodeNameLabel");
		label.setText(converter.getNodeName(node));

	}

	public NodeController(Node node) {
		this.node = node;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"NodeViewTemplate.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
