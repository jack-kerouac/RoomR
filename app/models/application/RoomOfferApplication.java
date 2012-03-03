package models.application;

import models.offer.RoomOffer;

public class RoomOfferApplication {
	
	public static enum State {
		// TODO: define useful states
		WAITING_FOR_INVITATION;
	}
	
	public State currentState;
		
	public String message;
	
	public RoomOffer roomOffer;
	
}
