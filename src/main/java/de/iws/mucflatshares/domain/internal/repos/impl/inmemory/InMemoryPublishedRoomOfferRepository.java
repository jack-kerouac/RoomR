package de.iws.mucflatshares.domain.internal.repos.impl.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.market.PublishedRoomOffer;
import de.iws.mucflatshares.domain.market.PublishedRoomOfferRepository;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

//@Repository
public class InMemoryPublishedRoomOfferRepository implements PublishedRoomOfferRepository {

	// @Autowired
	// private RoomOfferDao dao;

	private final Map<Long, PublishedRoomOffer> offers;

	public InMemoryPublishedRoomOfferRepository() {
		offers = new HashMap<Long, PublishedRoomOffer>();
	}

	public Collection<PublishedRoomOffer> findAllThatMatch(final RoomFilter request, final PersonalProfile profile) {
		return Collections2.filter(offers.values(), new Predicate<PublishedRoomOffer>() {

			@Override
			public boolean apply(PublishedRoomOffer input) {
				return request.apply(input.getOffer()) && input.getOffer().matches(profile);
			}

		});
	}

	public Collection<PublishedRoomOffer> findAllThatMatch(final RoomFilter request) {
		return Collections2.filter(offers.values(), new Predicate<PublishedRoomOffer>() {

			@Override
			public boolean apply(PublishedRoomOffer input) {
				return request.apply(input.getOffer());
			}

		});
	}

	public void add(PublishedRoomOffer offer) {
		offers.put(offer.getOffer().getId(), offer);
	}

	public PublishedRoomOffer findById(long offerId) {
		return offers.get(offerId);
	}

}
