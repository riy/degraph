package de.schauderhaft.degraph.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.schauderhaft.degraph.java.JavaHierarchicGraph;
import de.schauderhaft.degraph.java.NodeBuilder;
import de.schauderhaft.degraph.model.Node;

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
		DataProvider.setData(g);
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
		// s.show(null);

		final Map<Node, Node> categories = new HashMap<>();
		categories.put(asNode("Queen"), asNode("Heavy"));
		categories.put(asNode("Rook"), asNode("Heavy"));
		categories.put(asNode("Bishop"), asNode("Light"));
		categories.put(asNode("Knight"), asNode("Light"));
		categories.put(asNode("Light"), asNode("Figure"));
		categories.put(asNode("Heavy"), asNode("Figure"));

		JavaHierarchicGraph graph = new JavaHierarchicGraph() {

			@Override
			public Set<Node> topNodes() {
				// TODO Auto-generated method stub
				return categories.keySet();
			}

			@Override
			public Set<Node> contentsOf(Node group) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<Node> connectionsOf(Node node) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<Node> allNodes() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		s.show(graph);
	}

	private static Node asNode(String s) {
		return NodeBuilder.create() //
				.name(s) //
				.typ("chesspiece") //
				.createSimpleNode();
	}

}
