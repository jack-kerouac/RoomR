package notifiers;

import models.offer.RoomOffer;
import play.mvc.Mailer;

public class RoomrMailer extends Mailer {

	public final static String fromEmail = "noreply@roomr.de";
	
	public static void offerCreated(RoomOffer roomOffer) {
		setSubject("Anzeige angelegt");
		addRecipient(roomOffer.contactEmail);
		setFrom("RoomR Notifier <" + fromEmail + ">");
		send(roomOffer);
	}
	
}
