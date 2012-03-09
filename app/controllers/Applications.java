package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;

import com.google.common.base.Strings;

import facade.RoomOfferApplicationsFacade;
import facade.UserIsNotSeekerException;

public class Applications extends AbstractRoomrController {

	@Inject
	private static RoomOfferApplicationsFacade applicationsFacade;

	public static void apply(long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;

		RoomOfferApplication newApplication = applicationsFacade.apply(
				getCurrentUser(), roomOfferId, application.message);

		Offers.viewOffer(roomOfferId);
	}

	public static void view(String gaeUserEmail) {
		if (Strings.isNullOrEmpty(gaeUserEmail))
			notFound("no user logged in");

		try {
			Set<RoomOfferApplication> applications = applicationsFacade
					.viewAllApplicationsForUser(gaeUserEmail);
			render(applications);
		} catch (UserIsNotSeekerException e) {
			notFound("user " + e.getUser()
					+ " is no seeker, thus has no applications");
		}
	}

}
