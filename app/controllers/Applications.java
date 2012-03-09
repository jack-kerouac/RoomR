package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import facade.RoomOfferApplicationsFacade;

public class Applications extends AbstractRoomrController {

	@Inject
	private static RoomOfferApplicationsFacade applicationsFacade;

	public static void apply(long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;

		RoomOfferApplication newApplication = applicationsFacade.apply(getCurrentUser(), roomOfferId,
				application.message);

		Offers.viewOffer(roomOfferId);
	}

	public static void view() {
		if (getCurrentUser() == null)
			notFound("no user logged in");

		Set<RoomOfferApplication> applications = getCurrentUser().applications;
		render(applications);
	}

}
