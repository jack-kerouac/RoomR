package facade;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import controllers.Offers;

public class RoomOfferApplicationsFacade {

	@Inject
	private RoomrUserRepository userRepository;

	@Inject
	private RoomOfferRepository roomOfferRepository;

	public void apply(String gaeUserEmail, long roomOfferId, String message) {
		RoomOffer offer = roomOfferRepository.find(roomOfferId);

		RoomrUser applicant = userRepository.findUser(gaeUserEmail);
		if (!applicant.isSeeker())
			throw new IllegalStateException("applicant must be seeker");
		
		RoomOfferApplication application = new RoomOfferApplication();
		application.currentState = State.WAITING_FOR_INVITATION;
		application.message = message;
		application.applicant = applicant;
		application.roomOffer = offer;

		applicant.seekerProfile.applications.add(application);

		userRepository.update(applicant);
		
		Offers.viewOffer(roomOfferId);
	}

}
