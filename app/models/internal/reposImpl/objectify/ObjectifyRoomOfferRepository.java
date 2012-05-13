package models.internal.reposImpl.objectify;

import java.util.Set;

import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import play.modules.objectify.Datastore;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Query;

public class ObjectifyRoomOfferRepository implements RoomOfferRepository {

	@Override
	public void add(RoomOffer offer) {
		Datastore.put(offer);
	}

	@Override
	public Set<RoomOffer> findAll() {
		Query<RoomOffer> query = Datastore.query(RoomOffer.class);
		return ImmutableSet.copyOf(query);
	}

	@Override
	public RoomOffer find(long id) {
		return Datastore.find(RoomOffer.class, id, false);
	}

	@Override
	public void remove(RoomOffer offer) {
		Datastore.delete(offer);
	}

	@Override
	public void update(RoomOffer offer) {
		Datastore.put(offer);
	}

}