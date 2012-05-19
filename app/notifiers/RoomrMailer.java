package notifiers;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import models.offer.RoomOfferTokenService;
import play.modules.guice.InjectSupport;
import play.mvc.Mailer;

@InjectSupport
public class RoomrMailer extends Mailer {

	public final static String fromEmail = "noreply@roomr-munich.appspotmail.com";
	
	@Inject
	private static RoomOfferTokenService tokenService;
	
	public static void offerCreated(RoomOffer roomOffer) {
		setSubject("Anzeige angelegt");
		addRecipient(roomOffer.contactEmail);
		setFrom("RoomR Notifier <" + fromEmail + ">");
		
		String authToken = tokenService.createTokenForRoomOffer(roomOffer);
		send(roomOffer, authToken);
	}

	public static void newApplication(RoomOffer roomOffer, RoomOfferApplication application) {
		setSubject("Neue Bewerbung");
		addRecipient(roomOffer.contactEmail);
		setFrom("RoomR Notifier <" + fromEmail + ">");
		
		send(roomOffer, application);
	}
	
}
