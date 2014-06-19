package de.schauderhaft.degraph.java;

import de.schauderhaft.degraph.model.Node;

class ConstantCategorizer implements Categorizer {

	private final Node fixedValue;

	public ConstantCategorizer(Node fixedValue) {
		this.fixedValue = fixedValue;
	}

	@Override
	public Node categoryOf(Object node) {
		return fixedValue;
	}
}
