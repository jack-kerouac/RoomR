package facade;

import java.util.List;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.common.Age;
import models.common.Gender;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.ranking.OfferRanker;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.user.RoomrUser;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class SeekerFacade {

	@Inject
	private RoomOfferRepository roomOfferRepository;

	@Inject
	private RoomOfferApplicationRepository roomOfferApplicationRepository;

	@Inject
	private OfferRanker ranker;

	@Inject
	private NotificationService notificationService;

	public RoomOfferApplication apply(RoomrUser applicant, long roomOfferId, String message) {
		RoomOffer offer = roomOfferRepository.find(roomOfferId);

		// CREATE APPLICATION
		RoomOfferApplication application = new RoomOfferApplication();
		application.currentState = State.WAITING_FOR_INVITATION;
		application.message = message;
		application.setApplicant(applicant);
		application.setRoomOffer(offer);
		roomOfferApplicationRepository.add(application);

		// NOTIFY FLATSHARE
		notificationService.notifyFlatshareOfNewApplication(offer, application);

		return application;
	}

	public List<ScoredRoomOffer> search(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
		return ranker.search(request, seekerAge, seekerGender);
	}

	public void removeRoomOfferApplication(RoomOfferApplication application) {
		// check preconditons
		RoomOffer associatedOffer = application.getRoomOffer();
		Preconditions.checkState(associatedOffer.currentState == RoomOffer.State.PUBLIC,
				"Room Offer has to be in state public");
		Preconditions.checkState(application.currentState != RoomOfferApplication.State.ACCEPTED,
				"Application cannot be deleted when in state ACCEPTED");

		// notify flatshare if seeker has already been invited
		if (application.currentState == RoomOfferApplication.State.INVITED) {
			notificationService.notifyFlatshareOfRemovedApplication(associatedOffer, application);
		}

		roomOfferApplicationRepository.remove(application);
	}

	// TODO (Flo): This method does not really belong to this facade, since it
	// is called from
	// various points
	// TODO (Flo): throw exception here, if offer not found?
	public RoomOffer findOffer(long id) {
		return roomOfferRepository.find(id);
	}

}
