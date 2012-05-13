package models.offer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RoomOfferTokenService {

	private final String SALT = "ROOMR!Secret";

	public String createTokenForRoomOffer(RoomOffer offer) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		String stringToHash = SALT + offer.id;
		byte[] bytesOfMessage = stringToHash.getBytes();
		byte[] byteHash = md.digest(bytesOfMessage);
		String hashString = new String(byteHash);
		return hashString;
	}
}
