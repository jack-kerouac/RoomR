package facade;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import facade.exception.RoomOfferUpdateException;

public class ResidentFacade {
	private NotificationService notificationService;

	public Flatshare createFlatshare(Flatshare newFlatshare) {
		return newFlatshare.save();
	}

	public Flatshare createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer) {
		Flatshare result = createFlatshare(newFlatshare);
		roomOffer.flatshare = newFlatshare;
		roomOffer.save();
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);

		// have to be refreshed as a roomOffer has been added...
		return result.refresh();
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
		flatshare.save();
		offer.save();
	}

	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

}