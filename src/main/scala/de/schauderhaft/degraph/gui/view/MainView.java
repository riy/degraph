package de.schauderhaft.degraph.gui.view;

import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainView extends Application {

	private final Set<Label> labels;

	public MainView(Set<Label> labels) {
		this.labels = labels;
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("degraph");

		StackPane sp = new StackPane();
		Image img = new Image("logo.PNG");
		ImageView imgView = new ImageView(img);
		sp.getChildren().add(imgView);

		sp.getChildren().addAll(labels);
		// Adding HBox to the scene
		Scene scene = new Scene(sp, imgView.getBoundsInParent().getWidth(),
				imgView.getBoundsInParent().getHeight());
		stage.setScene(scene);
		stage.show();
	}

}
