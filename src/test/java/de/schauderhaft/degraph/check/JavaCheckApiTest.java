package de.schauderhaft.degraph.check;

import static de.schauderhaft.degraph.check.Check.classpath;

import org.junit.Test;

public class JavaCheckApiTest {

	@Test
	public void canAccessClasspathConfigurationBuilder() {
		classpath();
	}

	@Test
	public void canDefineSlices() {
		classpath().withSlicing("blah", "whatever");
	}
}
