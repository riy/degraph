package de.schauderhaft.degraph.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.schauderhaft.degraph.java.ChessCategory;
import de.schauderhaft.degraph.java.JavaGraph;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.model.SimpleNode;

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

		Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

		// Adding HBox to the scene
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void show(JavaHierarchicGraph g) {
		DataProvider.getInstance().setData(g);
		launch(new String[0]);
	}

	/**
	 * TODO: delete this entrypoint
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GuiStarter s = new GuiStarter();

		JavaGraph graph = new JavaGraph(new ChessCategory());
		graph.connect(node("King"), node("Queen"));
		graph.connect(node("Queen"), node("Rook"));
		graph.connect(node("Rook"), node("Bishop"));
		graph.connect(node("Rook"), node("Knight"));
		graph.connect(node("Knight"), node("Pawn"));
		graph.connect(node("Bishop"), node("Pawn"));

		graph.save("chess.graphml");
		s.show(graph);
	}

	private static SimpleNode node(String s) {
		return new SimpleNode(s, s);
	}
}
