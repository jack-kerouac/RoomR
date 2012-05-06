package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import facade.UserFacade;

@InjectSupport
public class Dashboard extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;

	public static void view() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();;
		if (loggedInUser.isPresent()) {
			Set<RoomOfferApplication> myApplications = loggedInUser.get().getApplications();

			if (loggedInUser.get().hasFlatshare()) {
				// Flatshare myFlatshare = loggedInUser.get().getFlatshare();
				// Set<RoomOfferApplication> flatshareApplications = repository
				// .findAllApplicationsFor(currentUser.getFlatshare());
				// render(myApplications, myFlatshare, flatshareApplications);
				render(myApplications);
			} else {
				render(myApplications);
			}

		} else {
			notFound("no user logged in");
		}
	}

}
