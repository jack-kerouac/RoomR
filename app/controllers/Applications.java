package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import facade.SeekerFacade;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

@InjectSupport
public class Applications extends AbstractRoomrController {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static UserFacade userFacade;

	public static void apply(long roomOfferId, RoomOfferApplication application) throws NoUserLoggedInException {
		application.currentState = State.WAITING_FOR_INVITATION;

		seekerFacade.apply(userFacade.getLoggedInUser(), roomOfferId, application.message);

		Offers.viewOffer(roomOfferId);
	}

	public static void view() {
		try {
			RoomrUser currentUser = userFacade.getLoggedInUser();

			Set<RoomOfferApplication> applications = currentUser.getApplications();
			render(applications);
		} catch (NoUserLoggedInException e) {
			notFound("no user logged in");
		}
	}

}
