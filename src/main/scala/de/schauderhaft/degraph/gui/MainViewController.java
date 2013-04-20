package de.schauderhaft.degraph.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainViewController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane ContentView;

	@FXML
	private AnchorPane NodeView;

	@FXML
	private AnchorPane mainView;

	@FXML
	void onMouseClicked(MouseEvent event) {
	}

	@FXML
	void initialize() {
		assert ContentView != null : "fx:id=\"ContentView\" was not injected: check your FXML file 'MainView.fxml'.";
		assert NodeView != null : "fx:id=\"NodeView\" was not injected: check your FXML file 'MainView.fxml'.";
		assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'MainView.fxml'.";

	}
}
