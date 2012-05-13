package models.internal.reposImpl.objectify;

import java.util.Set;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import play.modules.objectify.Datastore;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Query;

public class ObjectifyRoomOfferApplicationRepository implements RoomOfferApplicationRepository {

	@Override
	public void add(RoomOfferApplication newApplication) {
		Datastore.put(newApplication);
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

}