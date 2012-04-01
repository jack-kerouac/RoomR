package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.flatshare.Flatshare;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

@InjectSupport
public class Dashboard extends AbstractRoomrController {

	@Inject
	private static RoomOfferApplicationRepository repository;

	public static void view() {
		RoomrUser currentUser = getCurrentUser();
		if (currentUser == null)
			notFound("no user logged in");

		Set<RoomOfferApplication> myApplications = currentUser.getApplications();

		if (currentUser.hasFlatshare()) {
			Flatshare myFlatshare = currentUser.getFlatshare();
			Set<RoomOfferApplication> flatshareApplications = repository
					.findAllApplicationsFor(currentUser.getFlatshare().roomOffer);
			render(myApplications, myFlatshare, flatshareApplications);
		}
		else
			render(myApplications);
	}

}
