package models.notification;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import notifiers.RoomrMailer;

public class PlayMailNotificationService implements NotificationService {

	@Override
	public void notifyFlatshareOfCreatedOffer(RoomOffer offer) {
		RoomrMailer.offerCreated(offer);
	}

	@Override
	public void notifyFlatshareOfNewApplication(RoomOffer offer, RoomOfferApplication application) {
		RoomrMailer.newApplication(offer, application);
	}

	@Override
	public void notifyFlatshareOfRemovedApplication(RoomOffer offer, RoomOfferApplication application) {
		RoomrMailer.applicationRemoved(offer, application);
	}

}
