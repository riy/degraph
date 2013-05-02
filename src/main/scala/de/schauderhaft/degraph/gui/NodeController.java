package de.schauderhaft.degraph.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import de.schauderhaft.degraph.model.Node;

public class NodeController extends AnchorPane {

	public static final String NODE_NAME_LABEL = "#nodeNameLabel";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private final Node node;

	private final NodeLabelConverter converter = new NodeLabelConverter();

	@FXML
	void initialize() {

		initalizeLabels();

	}

	private void initalizeLabels() {
		Label label = (Label) this.lookup(NODE_NAME_LABEL);
		label.setText(converter.getNodeName(node));
	}

	public NodeController(Node node) {
		this.node = node;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"NodeView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println(converter.getNodeName(node) + " clicked");
	}

}
