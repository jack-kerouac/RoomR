package de.iws.mucflatshares.technical.guice;

import com.google.inject.Singleton;

public class A implements I {

	@Override
	public void hello() {
		System.out.println("Hello A");
	}

}
