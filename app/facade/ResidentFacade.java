package facade;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.RoomOfferTokenService;
import notifiers.RoomrMailer;
import facade.exception.RoomOfferUpdateException;

public class ResidentFacade {

	private FlatshareRepository flatshareRepository;
	private RoomOfferRepository roomOfferRepository;
	private RoomOfferTokenService roomOfferTokenService;

	public void createFlatshareAndOffer(Flatshare newFlatshare, RoomOffer roomOffer) {
		flatshareRepository.add(newFlatshare);
		roomOffer.setFlatshare(newFlatshare);
		roomOfferRepository.add(roomOffer);
		RoomrMailer.offerCreated(roomOffer);
	}

	/**
	 * Updates a room offer and its associated flatshare and validates the salt
	 * value for the update request.
	 * 
	 * @param offer
	 *            the offer to update
	 * @param flatshare
	 *            the flatshare for which the offer should be updated
	 * @param hash
	 *            the associated hash which should be checked for the update
	 *            process.
	 * @throws RoomOfferUpdateException
	 *             when the room offer doesn't belong to the flatshare or the
	 *             given hash is invalid
	 */
	public void updateRoomOfferForFlatshare(RoomOffer offer, Flatshare flatshare, String hash)
			throws RoomOfferUpdateException {
		// check if the offer really belongs to the given flatshare
		if (flatshare.id != offer.getFlatshare().id) {
			throw new RoomOfferUpdateException("Room Offer doesn't belong to the given flatshare");
		}

		// check hash
		String expectedHash = roomOfferTokenService.createTokenForRoomOffer(offer);
		if (!expectedHash.equals(hash)) {
			throw new RoomOfferUpdateException("Incorrect hash supplied for update request");
		}

		// hash ok -> update
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
	public void setRoomOfferTokenService(RoomOfferTokenService tokenService) {
		this.roomOfferTokenService = tokenService;
	}

}