package controllers;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import facade.RoomOfferApplicationFacade;

public class Applications extends AbstractRoomrController {

	@Inject
	private static RoomOfferApplicationFacade applicationFacade;

	public static void apply(String gaeUserEmail, long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;
		
		applicationFacade.apply(gaeUserEmail, roomOfferId, application.message);
	}
	
}
