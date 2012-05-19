package models.notification;

import models.offer.RoomOffer;

public interface NotificationService {

	public void notifyFlatshareOfCreatedOffer(RoomOffer offer);
	
}
