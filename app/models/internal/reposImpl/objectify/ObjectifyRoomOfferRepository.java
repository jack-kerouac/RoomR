package models.internal.reposImpl.objectify;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyService;

import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;

public class ObjectifyRoomOfferRepository implements RoomOfferRepository{

	@Override
	public void add(RoomOffer offer) {
		Datastore.put(offer);
	}

	@Override
	public Set<RoomOffer> findAll() {
		Objectify objectifyService = ObjectifyService.begin();
		Query<RoomOffer> q = objectifyService.query(RoomOffer.class);
		HashSet<RoomOffer> result = new HashSet<RoomOffer>();
		for (RoomOffer roomOffer: q) {
			result.add(roomOffer);
		}
		return result;
	}

	@Override
	public RoomOffer find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(RoomOffer offer) {
		// TODO Auto-generated method stub
		
	}


}