package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.user.RoomrUser;
import play.data.validation.Required;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import facade.SeekerFacade;
import facade.UserFacade;

@InjectSupport
public class Dashboard extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;
	@Inject
	private static SeekerFacade seekerFacade;

	public static void view() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (loggedInUser.isPresent()) {
			Set<RoomOfferApplication> myApplications = loggedInUser.get().getApplications();

			if (loggedInUser.get().hasFlatshare()) {
				Flatshare myFlatshare = loggedInUser.get().getFlatshare();
				System.out.println("my flatshare");
				System.out.println(myFlatshare.getRoomOffer());

				// Set<RoomOfferApplication> flatshareApplications = repository
				// .findAllApplicationsFor(currentUser.getFlatshare());
				// render(myApplications, myFlatshare, flatshareApplications);
				// render(myApplications);
			} else {
				render(myApplications);
			}

		} else {
			notFound("no user logged in");
		}
	}

	public static void removeApplication(@Required Long id) {
		seekerFacade.removeRoomOfferApplication(id);
		Dashboard.view();
	}

}
