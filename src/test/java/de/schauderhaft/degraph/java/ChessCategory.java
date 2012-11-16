package de.schauderhaft.degraph.java;

import java.util.HashMap;
import java.util.Map;

public class ChessCategory implements Categorizer {
	private final Map<String, String> categories = new HashMap<>();

	{
		categories.put("Queen", "Heavy");
		categories.put("Rook", "Heavy");
		categories.put("Bishop", "Light");
		categories.put("Knight", "Light");
		categories.put("Light", "Figure");
		categories.put("Heavy", "Figure");
	}

	@Override
	public Object categoryOf(Object node) {
		String result = categories.get(node);
		if (result == null)
			return node;
		else
			return result;
	}

}
