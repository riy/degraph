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

	private static JavaHierarchicGraph graph;

	public GuiStarter() {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (graph == null) {
			throw new NullPointerException("JavaHierarchicGraph is null!");
		}
		primaryStage.setTitle("Degraph");

		Parent root = new MainViewController(graph);

		// Adding HBox to the scene
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void show(JavaHierarchicGraph graph) {
		GuiStarter.graph = graph;
		launch(new String[0]);
	}

}
