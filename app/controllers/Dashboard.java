package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.data.validation.Required;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import facade.ResidentFacade;
import facade.SeekerFacade;
import facade.UserFacade;

@InjectSupport
public class Dashboard extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;
	@Inject
	private static SeekerFacade seekerFacade;
	@Inject
	private static ResidentFacade residentFacade;

	public static void view() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (loggedInUser.isPresent()) {
			Set<RoomOfferApplication> myApplications = loggedInUser.get().getApplications();

			if (loggedInUser.get().hasFlatshare()) {
				Flatshare myFlatshare = loggedInUser.get().getFlatshare();
				RoomOffer offerForResidentsFlatshare = loggedInUser.get().getFlatshare().getRoomOffer();
				Set<RoomOfferApplication> flatshareApplications = offerForResidentsFlatshare.getApplications();
				System.out.println(flatshareApplications);
				render(myApplications, myFlatshare, flatshareApplications);
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

	public static void inviteApplicant(@Required Long roaId) {
		residentFacade.inviteApplicant(roaId);
		Dashboard.view();
	}

}
