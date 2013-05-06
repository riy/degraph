package de.schauderhaft.degraph.gui;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import de.schauderhaft.degraph.gui.util.FXMLUtil;
import de.schauderhaft.degraph.model.Node;

public class NodeController extends AnchorPane {

	public static final String LABEL_NAME = "#nodeNameLabel";
	public static final String PANE_NAME = "#nodeView";
	public static final double WIDTH = 130.0;
	public static final double HEIGHT = 79.0;

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
		Label label = (Label) this.lookup(LABEL_NAME);
		label.setText(converter.getNodeName(node));
	}

	public NodeController(Node node) {
		this.node = node;
		FXMLUtil.loadAndSetController(this, "NodeView.fxml");

	}

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println(converter.getNodeName(node) + " clicked");
	}

	public void setLayoutXForAllPanes(int x) {
		this.setLayoutX(x);
		this.setLayoutY(10);
		System.out.println("Controller Pos" + this.getLayoutX());
	}

	public void resizeNodeContentView() {
		Pane nodeContentView = (Pane) this.lookup(PANE_NAME);
		Label nodeName = (Label) this.lookup(LABEL_NAME);
		int size = nodeContentView.getChildrenUnmodifiable().size();
		System.out.println("NodeContenView Size: " + size);
		if (size > 0) {
			System.out.println(this.toString() + " hat Kinder: " + size);
			nodeContentView.setPrefWidth(WIDTH * 1.4 * size);
			nodeContentView.setPrefHeight(HEIGHT * 1.4 * size);
			nodeName.setPrefWidth(WIDTH * 1.4 * size);

		} else {
			nodeContentView.setPrefWidth(WIDTH);
			nodeName.setPrefWidth(WIDTH);

		}
	}

	public void setWidthForAllPanes(Point paneSize) {
		this.setPrefWidth(paneSize.getX());
		this.setPrefHeight(paneSize.getY());
		((Pane) this.lookup(PANE_NAME)).setPrefWidth(paneSize.getX());
		((Pane) this.lookup(PANE_NAME)).setPrefHeight(paneSize.getY());
		((Label) this.lookup(LABEL_NAME)).setPrefWidth(paneSize.getX());

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("this.size: " + this.getPrefHeight() + " x "
				+ this.getPrefWidth() + "  x | y" + this.getLayoutX() + " | "
				+ this.getLayoutY() + "\n");
		Label Label = (Label) this.lookup(LABEL_NAME);
		sb.append("Label.size: " + Label.getPrefHeight() + " x "
				+ Label.getPrefWidth() + " x | y" + Label.getLayoutX() + " | "
				+ Label.getLayoutY() + "\n");
		Pane pane = (Pane) this.lookup(PANE_NAME);
		sb.append("Pane.size: " + pane.getPrefHeight() + " x "
				+ pane.getPrefWidth() + " x | y" + pane.getLayoutX() + " | "
				+ pane.getLayoutY());

		return sb.toString();
	}
}
