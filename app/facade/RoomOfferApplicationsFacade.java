package facade;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.User;

public class RoomOfferApplicationsFacade {

	@Inject
	private RoomOfferRepository roomOfferRepository;

	@Inject
	private RoomOfferApplicationRepository roomOfferApplicationRepository;

	public RoomOfferApplication apply(RoomrUser applicant, long roomOfferId, String message) {
		RoomOffer offer = roomOfferRepository.find(roomOfferId);

		// CREATE APPLICATION
		RoomOfferApplication application = new RoomOfferApplication();
		application.currentState = State.WAITING_FOR_INVITATION;
		application.message = message;
		// TODO set applicant for this application
//		application.applicant = applicant;
		application.roomOffer = offer;
		roomOfferApplicationRepository.add(application);

		return application;
	}
}
