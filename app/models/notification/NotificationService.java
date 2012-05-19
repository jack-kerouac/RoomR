package models.notification;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;

public interface NotificationService {

	public void notifyFlatshareOfCreatedOffer(RoomOffer offer);

	public void notifyFlatshareOfNewApplication(RoomOffer offer, RoomOfferApplication application);
	
}
