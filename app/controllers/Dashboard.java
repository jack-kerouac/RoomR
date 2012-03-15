package controllers;

import java.util.Set;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.user.RoomrUser;

public class Dashboard extends AbstractRoomrController {

	public static void view() {
		RoomrUser currentUser = getCurrentUser();
		if (currentUser == null)
			notFound("no user logged in");

		Set<RoomOfferApplication> myApplications = currentUser.applications;
		Flatshare myFlatshare = currentUser.currentFlatshare;
		// TODO: add all applications for the room offer of the flatshare the user's living in
		// Set<RoomOfferApplication> flatshareApplications;
		render(myApplications, myFlatshare);
	}

}
