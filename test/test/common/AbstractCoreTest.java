package test.common;
import modules.CoreModule;

import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractCoreTest {

	protected Injector injector;

	@Before
	public final void setupInjector() {
		injector = Guice.createInjector(new CoreModule(), new InfrastructureMockModule());
	}

}
