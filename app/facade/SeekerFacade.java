package facade;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;

public class SeekerFacade {

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
		application.setApplicant(applicant);
		application.setRoomOffer(offer);
		roomOfferApplicationRepository.add(application);
		return application;
	}

	// TODO (Flo): This method does not really belong to this facade, since it is called from various points
	// TODO (Flo): throw exception here, if offer not found?
	public RoomOffer findOffer(long id) {
		return roomOfferRepository.find(id);
	}

}
