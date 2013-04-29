package de.schauderhaft.degraph.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;

/**
 * open the degraph visualisation
 * 
 */
public class GuiStarter extends javafx.application.Application {

	public GuiStarter() {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Degraph");

		MainViewController controller = new MainViewController();
		Parent root = controller;
		// Parent root =
		// FXMLLoader.load(getClass().getResource("MainView.fxml"));

		// Adding HBox to the scene
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void show(JavaHierarchicGraph g) {
		DataProvider.getInstance().setData(g);
		launch(new String[0]);
	}

}
