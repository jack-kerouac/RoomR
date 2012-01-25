package de.iws.mucflatshares.domain.internal.repos.impl.appengine;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.market.PublishedRoomOffer;
import de.iws.mucflatshares.domain.market.PublishedRoomOfferRepository;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

@Repository
public class ObjectifyPublishedRoomOfferRepository implements PublishedRoomOfferRepository {

	@PostConstruct
	public void init() {
		// ObjectifyService.register(PublishedRoomOffer.class);
	}

	@Override
	public Collection<PublishedRoomOffer> findAllThatMatch(RoomFilter request, PersonalProfile profile) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<PublishedRoomOffer> findAllThatMatch(RoomFilter request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(PublishedRoomOffer offer) {
		Objectify ofy = ObjectifyService.begin();

		Key<PublishedRoomOffer> publishedOfferKey = new Key<PublishedRoomOffer>(PublishedRoomOffer.class, offer
				.getOffer().getId());

		Key<PublishedRoomOffer> put = ofy.put(offer);
	}

	@Override
	public PublishedRoomOffer findById(long offerId) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(PublishedRoomOffer.class, offerId);
	}
}
