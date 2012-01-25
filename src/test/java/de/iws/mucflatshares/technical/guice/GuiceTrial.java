package de.iws.mucflatshares.technical.guice;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

public class GuiceTrial {

	@Test
	public void testGuice() throws Exception {
	    Injector injector = Guice.createInjector(new IModule());
	    Client client1 = injector.getInstance(Client.class);
	    Client client2 = injector.getInstance(Key.get(Client.class));
	    client1.saySomthing();
	    client2.saySomthing();
	}
	
}
