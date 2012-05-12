package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.user.RoomrUser;
import play.mvc.Result;

import com.google.common.base.Optional;

import facade.SeekerFacade;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

public class Applications extends AbstractRoomrController {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static UserFacade userFacade;

	public static void apply(long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;

		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (!loggedInUser.isPresent())
			throw new NoUserLoggedInException();

		seekerFacade
				.apply(loggedInUser.get(), roomOfferId, application.message);

		// Offers.viewOffer(roomOfferId);
	}

	public static Result view() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();

		if (loggedInUser.isPresent()) {
			Set<RoomOfferApplication> applications = loggedInUser.get().applications;

			// TODO:
			return ok("");
		} else {
			return notFound("no user logged in");
		}
	}

}
