package de.schauderhaft.degraph.gui;

import javafx.stage.Stage;

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
		primaryStage.show();
	}

	public void show() {
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
		s.show();
	}
}
