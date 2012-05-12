package facade;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.offer.RoomOffer;
import models.user.RoomrUser;

public class SeekerFacade {

	public RoomOfferApplication apply(RoomrUser applicant, long roomOfferId,
			String message) {
		RoomOffer offer = findOffer(roomOfferId);

		// CREATE APPLICATION
		RoomOfferApplication application = new RoomOfferApplication();
		application.currentState = State.WAITING_FOR_INVITATION;
		application.message = message;
		application.applicant = applicant;
		application.roomOffer = offer;

		RoomOfferApplication.create(application);
		return application;
	}

	public RoomOffer findOffer(long id) {
		return RoomOffer.find.byId(id);
	}

}
