package models.notification;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import models.user.RoomrUser;

public interface NotificationService {

	public void notifyFlatshareOfCreatedOffer(RoomOffer offer);

	public void notifyFlatshareOfNewApplication(RoomOffer offer, RoomOfferApplication application);

	public void notifyFlatshareOfRemovedApplication(RoomOffer offer, RoomOfferApplication application);

	public void notifyUserOfInvitation(RoomrUser applicant, RoomOfferApplication application);

}
