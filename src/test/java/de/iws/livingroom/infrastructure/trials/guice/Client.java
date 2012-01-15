package de.iws.livingroom.infrastructure.trials.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Client {

	private final I i;

	private long id;

	@Inject
	public Client(I i, @Named("id") long id) {
		super();
		this.i = i;
		this.id = id;
	}

	public void saySomthing() {
		System.out.println("Client " + id + " I says:");
		i.hello();
	}

}
