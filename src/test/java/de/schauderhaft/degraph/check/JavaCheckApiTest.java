package de.schauderhaft.degraph.check;

import org.junit.Test;
import static de.schauderhaft.degraph.check.Check.classpath;

public class JavaCheckApiTest {

	@Test
	public void canAccessClasspathConfigurationBuilder() {
		classpath();
	}

	@Test
	public void canDefineSlices() {
		classpath().withSlicing("blah", "whatever");
	}

	@Test
	public void canUseAllow() {
		classpath().withSlicing("blah", "whatever").allow("a", "b", "c");
	}

	@Test
	public void canUseAllowDirect() {
		classpath().withSlicing("blah", "whatever").allowDirect("a", "b", "c");
	}

	@Test
	public void canUseAnyOf() {
		JLayer.anyOf("b", "c", "d");
	}

	@Test
	public void canUseOneOf() {
		JLayer.oneOf("b", "c", "d");
	}
}
