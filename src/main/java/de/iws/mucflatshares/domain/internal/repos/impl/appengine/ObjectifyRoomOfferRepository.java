package de.iws.mucflatshares.domain.internal.repos.impl.appengine;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.offering.offer.RoomOfferRepository;

@Repository
public class ObjectifyRoomOfferRepository implements RoomOfferRepository {

	@PostConstruct
	public void init() {
//		ObjectifyService.register(RoomOffer.class);
	}

	@Override
	public void add(RoomOffer offer) {
		Objectify ofy = ObjectifyService.begin();

		Key<RoomOffer> key = ofy.put(offer);
	}

	@Override
	public RoomOffer findById(long offerId) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(RoomOffer.class, offerId);
	}

}
