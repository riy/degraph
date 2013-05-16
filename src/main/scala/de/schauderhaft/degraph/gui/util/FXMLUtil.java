package de.schauderhaft.degraph.gui.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

/**
 * Util for loading Fxml und setting controller to them.
 */
public class FXMLUtil {

	public static void loadAndSetController(Object controller, String viewFxml) {
		FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass()
				.getResource(viewFxml));
		fxmlLoader.setRoot(controller);
		fxmlLoader.setController(controller);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
