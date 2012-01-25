package de.iws.mucflatshares.technical.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public class IModule extends AbstractModule {
	
	@Override
	protected void configure() {
//		bind(I.class).to(B.class);
		bindConstant().annotatedWith(Names.named("id")).to(62l);
	}
	
	@Provides
	@Singleton
	public I provideI() {
		if(Math.random() > 0.5)
			return new A();
		else
			return new B();
	}
	
}