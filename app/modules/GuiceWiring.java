package modules;

import play.modules.guice.GuiceSupport;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class GuiceWiring extends GuiceSupport {
	
	protected Injector configure() {
		return Guice.createInjector(new CoreModule(), new InfrastructureModule());
	}
	
}