package de.iws.livingroom.infrastructure.trials.appengine.twig.model;

public class LandscapePhoto extends Photo {

	private Coordinates location;

	public Coordinates getLocation() {
		return location;
	}

	public LandscapePhoto setLocation(Coordinates location) {
		this.location = location;
		return this;
	}

}
