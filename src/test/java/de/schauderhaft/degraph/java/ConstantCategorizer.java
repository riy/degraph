package de.schauderhaft.degraph.java;

class ConstantCategorizer implements Categorizer {

	private final Object fixedValue;

	public ConstantCategorizer(Object fixedValue) {
		this.fixedValue = fixedValue;
	}

	@Override
	public Object categoryOf(Object node) {
		return fixedValue;
	}
}
