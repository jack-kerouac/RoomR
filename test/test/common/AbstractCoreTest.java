package test.common;

import modules.CoreModule;

import org.junit.Before;

import play.test.UnitTest;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This class inherits from {@link UnitTest} so that all core tests are displayed in the tests
 * overview of play (.../@tests).
 * 
 * @author "Florian Rampp (Florian.Rampp@jambit.com)"
 * 
 */
public abstract class AbstractCoreTest extends UnitTest {

	protected Injector injector;

	@Before
	public final void setupInjector() {
		injector = Guice.createInjector(new CoreModule(), new InfrastructureMockModule());
	}

}
