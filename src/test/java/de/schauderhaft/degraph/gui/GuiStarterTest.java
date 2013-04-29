package de.schauderhaft.degraph.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class GuiStarterTest {

	private GuiStarter underTest = null;

	@Before
	public void init() {
		underTest = new GuiStarter();
	}

	@Test
	public void shouldNotNull() {
		assertNotNull(underTest);
	}

	// @Test(expected = NullPointerException.class)
	// public void shouldGetException1() throws Exception {
	// underTest.show(null);
	// underTest.start(null);
	//
	// }
	//
	// @Test
	// public void shouldGetException2() {
	// underTest.show(null);
	//
	// try {
	// underTest.start(null);
	// } catch (Exception e) {
	// assertTrue(e instanceof NullPointerException);
	// }
	// }
}
