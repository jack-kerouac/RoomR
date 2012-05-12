package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.user.RoomrUser;
import play.mvc.Result;

import com.google.common.base.Optional;

import facade.UserFacade;

public class Dashboard extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;

	public static Result view() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		;
		if (loggedInUser.isPresent()) {
			Set<RoomOfferApplication> myApplications = loggedInUser.get().applications;

			if (loggedInUser.get().hasFlatshare()) {
				// Flatshare myFlatshare = loggedInUser.get().getFlatshare();
				// Set<RoomOfferApplication> flatshareApplications = repository
				// .findAllApplicationsFor(currentUser.getFlatshare());
				// render(myApplications, myFlatshare, flatshareApplications);

				// TODO: render(myApplications);
				return ok("");
			} else {
				// TODO: render(myApplications);
				return ok("");
			}

		} else {
			return notFound("no user logged in");
		}
	}

}
