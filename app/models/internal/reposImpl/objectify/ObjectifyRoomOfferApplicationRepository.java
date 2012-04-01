package models.internal.reposImpl.objectify;

import java.util.Set;

import play.modules.objectify.Datastore;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;

public class ObjectifyRoomOfferApplicationRepository implements RoomOfferApplicationRepository {

	@Override
	public void add(RoomOfferApplication newApplication) {
		Datastore.put(newApplication);
	}

	@Override
	public Set<RoomOfferApplication> findAllApplicationsFor(RoomOffer roomOffer) {
		// TODO Auto-generated method stub
		return null;
	}

}
