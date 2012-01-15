package de.iws.livingroom.infrastructure.trials.appengine.twig.model;

import java.util.Collection;

public final class PhotoWithPersons extends Photo {

	private Collection<String> names;

	public Collection<String> getNames() {
		return names;
	}

	public PhotoWithPersons setNames(Collection<String> names) {
		this.names = names;
		return this;
	}

}
