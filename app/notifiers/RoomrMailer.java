package notifiers;

import models.offer.RoomOffer;

public class RoomrMailer {

	public final static String fromEmail = "noreply@roomr-munich.appspotmail.com";

	public static void offerCreated(RoomOffer roomOffer) {
		// setSubject("Anzeige angelegt");
		// addRecipient(roomOffer.contactEmail);
		// setFrom("RoomR Notifier <" + fromEmail + ">");
		// send(roomOffer);
	}

}
