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

import com.google.common.base.Optional;

public class SeekerFacade {
	@Inject
	private OfferRanker ranker;

	@Inject
	private NotificationService notificationService;

	public RoomOfferApplication apply(RoomrUser applicant, long roomOfferId,
			String message) {

		RoomOffer offer = RoomOffer.findById(roomOfferId);

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

	public List<ScoredRoomOffer> search(RoomRequest request,
			Optional<Integer> seekerAge, Optional<Gender> seekerGender) {
		return ranker.search(request, seekerAge, seekerGender);
	}

	// TODO (Flo): This method does not really belong to this facade, since it
	// is called from
	// various points
	// TODO (Flo): throw exception here, if offer not found?
	public RoomOffer findOffer(long id) {
		return RoomOffer.findById(id);
	}

}
