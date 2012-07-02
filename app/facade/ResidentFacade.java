package facade;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import facade.exception.RoomOfferUpdateException;

@InjectSupport
public class ResidentFacade {

	private NotificationService notificationService;

	public Flatshare createFlatshare(Flatshare newFlatshare, Optional<RoomrUser> potentialCreator) {
		if (potentialCreator.isPresent()) {
			final RoomrUser creator = potentialCreator.get();

			if (creator.hasFlatshare())
				throw new RuntimeException("resident is already living in another flatshare: " + creator.flatshare);

			Flatshare ret = newFlatshare.save();
			creator.flatshare = newFlatshare;
			creator.save();

			// have to be refreshed as a resident has been added...
			return ret.refresh();
		} else {
			return newFlatshare.save();
		}
	}

	@Deprecated
	public Flatshare createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer,
			Optional<RoomrUser> potentialCreator) {
		Flatshare result = createFlatshare(newFlatshare, potentialCreator);
		roomOffer.flatshare = newFlatshare;
		roomOffer.currentState = RoomOffer.State.PUBLIC;
		roomOffer.save();
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);

		return result;
	}

	public void addOfferToFlatshare(Flatshare flatshare, RoomOffer roomOffer) {
		roomOffer.flatshare = flatshare;
		roomOffer.save();
		// this refresh should automatically add the new room offer to the
		// flatshare's collection of room offers
		flatshare.refresh();
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);
	}

	public Flatshare updateFlatshare(Flatshare flatshare) {
		return flatshare.merge();
	}

	/**
	 * Updates a room offer and its associated flatshare.
	 * 
	 * @param offer
	 *            the offer to update
	 * @param flatshare
	 *            the flatshare for which the offer should be updated
	 * @throws RoomOfferUpdateException
	 *             when the room offer doesn't belong to the flatshare
	 */
	public void updateRoomOfferForFlatshare(RoomOffer offer, Flatshare flatshare) throws RoomOfferUpdateException {
		// check if the offer really belongs to the given flatshare
		if (flatshare.id != offer.flatshare.id) {
			throw new RoomOfferUpdateException("Room Offer doesn't belong to the given flatshare");
		}

		// update entities
		flatshare.merge();
		offer.merge();
	}

	/**
	 * invites the applicant associated to the given application. the user is
	 * notified and the state of the room offer application is set to INVITED.
	 * 
	 * @param roomOfferApplicationId
	 *            id of the room offer application for which the applicant
	 *            should be invited.
	 */
	public void inviteApplicant(Long roomOfferApplicationId) {

		RoomOfferApplication application = RoomOfferApplication.findById(roomOfferApplicationId);

		// check preconditons
		Preconditions.checkArgument(application != null, "Couldn\'t find a application for the given id");
		Preconditions.checkState(application.currentState == RoomOfferApplication.State.WAITING_FOR_INVITATION,
				"Application has to be in state WAITING_FOR_INVITATION if an applicant should be invited but is in state "
						+ application.currentState);
		Preconditions.checkState(application.roomOffer.currentState == RoomOffer.State.PUBLIC,
				"Room Offer has to be in state public to invite an applicant");

		// advance ROA state
		application.currentState = RoomOfferApplication.State.INVITED;
		application.merge();

		// notify user
		this.notificationService.notifyUserOfInvitation(application.applicant, application);
	}

	@Inject
	public void setNotificationService(NotificationService service) {
		notificationService = service;
	}

}