package models.application;

import java.util.Set;

import models.offer.RoomOffer;

public interface RoomOfferApplicationRepository {

	public void add(RoomOfferApplication newApplication);
	
	public Set<RoomOfferApplication> findAllApplicationsFor(RoomOffer roomOffer);
	
}
