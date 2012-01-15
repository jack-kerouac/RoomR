package de.iws.livingroom;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.Guice;

public class CoreModuleTest {

	@Test
	public void testBindings() {
		Guice.createInjector(new CoreModule());
	}

}
