package facade;

import java.util.List;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.common.Gender;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.ranking.OfferRanker;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

@InjectSupport
public class SeekerFacade {

	@Inject
	private OfferRanker ranker;

	private NotificationService notificationService;

	public RoomOfferApplication apply(RoomrUser applicant, long roomOfferId, String message) {

		RoomOffer offer = RoomOffer.findById(roomOfferId);
		if (offer == null) {
			throw new IllegalStateException("room offer with ID " + roomOfferId + " not found");
		}

		// CREATE APPLICATION
		RoomOfferApplication application = new RoomOfferApplication();
		application.currentState = State.WAITING_FOR_INVITATION;
		application.message = message;
		application.applicant = applicant;
		application.roomOffer = offer;
		application.save();

		// NOTIFY FLATSHARE
		notificationService.notifyFlatshareOfNewApplication(offer, application);

		return application;
	}

	public List<ScoredRoomOffer> search(RoomRequest request, Optional<Integer> seekerAge, Optional<Gender> seekerGender) {
		return ranker.search(request, seekerAge, seekerGender);
	}

	public void removeRoomOfferApplication(Long applicationId) {
		// load room offer application
		RoomOfferApplication application = RoomOfferApplication.findById(applicationId);

		// check preconditons
		Preconditions.checkState(application != null, "No application could be found for the given id");
		RoomOffer associatedOffer = application.roomOffer;
		Preconditions.checkState(associatedOffer.currentState == RoomOffer.State.PUBLIC,
				"Room Offer has to be in state public");
		Preconditions.checkState(application.currentState != RoomOfferApplication.State.ACCEPTED,
				"Application cannot be deleted when in state ACCEPTED");

		// notify flatshare if seeker has already been invited
		if (application.currentState == RoomOfferApplication.State.INVITED) {
			notificationService.notifyFlatshareOfRemovedApplication(associatedOffer, application);
		}

		application.delete();
	}

	// TODO (Flo): This method does not really belong to this facade, since it
	// is called from
	// various points
	// TODO (Flo): throw exception here, if offer not found?
	public RoomOffer findOffer(long id) {
		return RoomOffer.findById(id);
	}

	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
