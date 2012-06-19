package facade;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;

import com.google.common.base.Preconditions;

import facade.exception.RoomOfferUpdateException;

public class ResidentFacade {

	private FlatshareRepository flatshareRepository;
	private RoomOfferRepository roomOfferRepository;
	private RoomOfferApplicationRepository roomOfferApplicationRepository;

	private NotificationService notificationService;

	public void createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer) {
		flatshareRepository.add(newFlatshare);
		roomOffer.setFlatshare(newFlatshare);
		roomOffer.currentState = RoomOffer.State.PUBLIC;
		roomOfferRepository.add(roomOffer);
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);
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
		if (flatshare.id != offer.getFlatshare().id) {
			throw new RoomOfferUpdateException("Room Offer doesn't belong to the given flatshare");
		}

		// update entities
		flatshareRepository.update(flatshare);
		roomOfferRepository.update(offer);
	}

	/**
	 * invites the applicant associated to the given application. the user is
	 * notified and the state of the room offer application is set to INVITED.
	 * 
	 * @param application
	 *            the application for which the associated applicant should be
	 *            invited.
	 */
	public void inviteApplicant(RoomOfferApplication application) {
		// check preconditons
		Preconditions.checkState(application.currentState == RoomOfferApplication.State.WAITING_FOR_INVITATION,
				"Application has to be in state WAITING_FOR_INVITATION if an applicant should be invited but is in state "
						+ application.currentState);
		Preconditions.checkState(application.getRoomOffer().currentState == RoomOffer.State.PUBLIC,
				"Room Offer has to be in state public to invite an applicant");

		// notify user
		this.notificationService.notifyUserOfInvitation(application.getApplicant(), application);

		// advance ROA state
		application.currentState = RoomOfferApplication.State.INVITED;
		this.roomOfferApplicationRepository.update(application);
	}

	@Inject
	public void setRoomOfferRepository(RoomOfferRepository roomOfferRepository) {
		this.roomOfferRepository = roomOfferRepository;
	}

	@Inject
	public void setFlatshareRepository(FlatshareRepository flatshareRepository) {
		this.flatshareRepository = flatshareRepository;
	}

	@Inject
	public void setRoomOfferApplicationRepository(RoomOfferApplicationRepository roomOfferApplicationRepository) {
		this.roomOfferApplicationRepository = roomOfferApplicationRepository;
	}

	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}