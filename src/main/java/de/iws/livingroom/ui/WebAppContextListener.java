package de.iws.livingroom.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import de.iws.livingroom.CoreModule;

public class WebAppContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new MyUiModule(), new CoreModule());
	}
}
