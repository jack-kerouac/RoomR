package facade;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import facade.exception.RoomOfferUpdateException;

public class ResidentFacade {

	private FlatshareRepository flatshareRepository;
	private RoomOfferRepository roomOfferRepository;

	private NotificationService notificationService;
	
	public void createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer) {
		flatshareRepository.add(newFlatshare);
		roomOffer.setFlatshare(newFlatshare);
		roomOfferRepository.add(roomOffer);
		notificationService.notifyResidentOfCreatedOffer(roomOffer);
	}

	/**
	 * Updates a room offer and its associated flatshare and validates the salt
	 * value for the update request.
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

	@Inject
	public void setRoomOfferRepository(RoomOfferRepository roomOfferRepository) {
		this.roomOfferRepository = roomOfferRepository;
	}

	@Inject
	public void setFlatshareRepository(FlatshareRepository flatshareRepository) {
		this.flatshareRepository = flatshareRepository;
	}
	
	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
}