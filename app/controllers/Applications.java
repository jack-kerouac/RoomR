package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import facade.RoomOfferApplicationsFacade;

@InjectSupport
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
		RoomrUser currentUser = getCurrentUser();
		if (currentUser == null)
			notFound("no user logged in");

		Set<RoomOfferApplication> applications = currentUser.applications;
		render(applications);
	}

}
