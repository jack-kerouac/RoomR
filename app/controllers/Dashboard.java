package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.flatshare.Flatshare;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

@InjectSupport
public class Dashboard extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;

	@Inject
	private static RoomOfferApplicationRepository repository;

	public static void view() {
		RoomrUser currentUser = null;
		try {
			currentUser = userFacade.getLoggedInUser();
		} catch (NoUserLoggedInException e) {
			notFound("no user logged in");
		}

		Set<RoomOfferApplication> myApplications = currentUser.getApplications();

		if (currentUser.hasFlatshare()) {
			Flatshare myFlatshare = currentUser.getFlatshare();
			// Set<RoomOfferApplication> flatshareApplications = repository
			// .findAllApplicationsFor(currentUser.getFlatshare());
			// render(myApplications, myFlatshare, flatshareApplications);
		} else
			render(myApplications);
	}

}
