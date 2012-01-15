package de.iws.livingroom.domain;

public class OfferFactory {

	private static long offerCounter = 1;
	
	public Offer create(Location location, RoomDetails roomDetails) {
		return new Offer(offerCounter++, location, roomDetails);
	}
	
}
