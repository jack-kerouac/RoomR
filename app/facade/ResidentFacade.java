package facade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import facade.exception.RoomOfferUpdateException;

import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import notifiers.RoomrMailer;

public class ResidentFacade {

	@Inject
	private FlatshareRepository flatshareRepository;

	@Inject
	private RoomOfferRepository roomOfferRepository;

	public final String SALT = "ROOMR_SECRET";

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
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RoomOfferUpdateException("Error during hash verification");
		}
		String stringToHash = SALT + offer.id;
		byte[] bytesOfMessage = stringToHash.getBytes();
		byte[] hashedBytes = md.digest(bytesOfMessage);
		String hashString = new String(hashedBytes);
		if (!hashString.equals(hash)) {
			throw new RoomOfferUpdateException("Incorrect hash supplied for update request");
		}

		// hash ok -> update
		flatshareRepository.update(flatshare);
		roomOfferRepository.update(offer);
	}
}