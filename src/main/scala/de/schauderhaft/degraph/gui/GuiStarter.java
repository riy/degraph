package de.schauderhaft.degraph.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
		primaryStage.setTitle("degraph");

		StackPane sp = new StackPane();
		Image img = new Image("logo.PNG");
		ImageView imgView = new ImageView(img);
		sp.getChildren().add(imgView);

		// Adding HBox to the scene
		Scene scene = new Scene(sp, imgView.getBoundsInParent().getWidth(),
				imgView.getBoundsInParent().getHeight());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void show(JavaHierarchicGraph g) {
		launch(new String[0]);
	}

	/**
	 * TODO: delete this entrypoint
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Geht");
		GuiStarter s = new GuiStarter();
		s.show(null);
	}
}
