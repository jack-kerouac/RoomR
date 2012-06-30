package models.internal.reposImpl.objectify;

import java.util.Set;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;
import play.modules.objectify.Datastore;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Query;

public class ObjectifyRoomOfferApplicationRepository implements RoomOfferApplicationRepository {

	@Override
	public void add(RoomOfferApplication newApplication) {
		Datastore.put(newApplication);
	}

	@Override
	public void update(RoomOfferApplication application) {
		Datastore.put(application);
	}

	@Override
	public Set<RoomOfferApplication> findAll() {
		Query<RoomOfferApplication> query = Datastore.query(RoomOfferApplication.class);
		return ImmutableSet.copyOf(query);
	}

	@Override
	public void remove(RoomOfferApplication application) {
		Datastore.delete(application);
	}

	@Override
	public RoomOfferApplication findApplication(Long applicationId) {
			return Datastore.find(RoomOfferApplication.class, applicationId, false);
	}
}