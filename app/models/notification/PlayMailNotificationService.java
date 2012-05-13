package models.notification;

import models.offer.RoomOffer;
import notifiers.RoomrMailer;

public class PlayMailNotificationService implements NotificationService {

	@Override
	public void notifyResidentOfCreatedOffer(RoomOffer offer) {
		RoomrMailer.offerCreated(offer);
	}

}
