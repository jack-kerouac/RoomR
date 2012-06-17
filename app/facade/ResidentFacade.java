package facade;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.notification.NotificationService;
import models.offer.RoomOffer;
import models.user.RoomrUser;

import com.google.common.base.Optional;

import facade.exception.RoomOfferUpdateException;

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

	public void addOfferToFlatshare(Flatshare flatshare, RoomOffer roomOffer) {
		roomOffer.flatshare = flatshare;
		roomOffer.save();
		// this refresh should automatically add the new room offer to the
		// flatshare's collection of room offers
		flatshare.refresh();
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);
	}

	@Deprecated
	public Flatshare createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer,
			Optional<RoomrUser> potentialCreator) {
		Flatshare result = createFlatshare(newFlatshare, potentialCreator);
		roomOffer.flatshare = newFlatshare;
		roomOffer.save();
		notificationService.notifyFlatshareOfCreatedOffer(roomOffer);

		// have to be refreshed as a roomOffer has been added...
		return result.refresh();
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
	public Flatshare updateRoomOfferForFlatshare(RoomOffer offer, Flatshare flatshare) throws RoomOfferUpdateException {
		// check if the offer really belongs to the given flatshare
		if (flatshare.id != offer.flatshare.id) {
			throw new RoomOfferUpdateException("Room Offer doesn't belong to the given flatshare");
		}

		// update entities
		Flatshare result = flatshare.merge();
		offer.merge();
		return result;
	}

	@Inject
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

}