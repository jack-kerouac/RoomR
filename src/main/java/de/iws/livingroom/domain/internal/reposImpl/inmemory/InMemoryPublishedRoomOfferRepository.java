package de.iws.livingroom.domain.internal.reposImpl.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import de.iws.livingroom.domain.common.PersonalProfile;
import de.iws.livingroom.domain.market.PublishedRoomOffer;
import de.iws.livingroom.domain.market.PublishedRoomOfferRepository;
import de.iws.livingroom.domain.seeking.request.FlatshareRequest;

//@Repository
public class InMemoryPublishedRoomOfferRepository implements PublishedRoomOfferRepository {

	// @Autowired
	// private RoomOfferDao dao;

	private final Map<Long, PublishedRoomOffer> offers;

	public InMemoryPublishedRoomOfferRepository() {
		offers = new HashMap<Long, PublishedRoomOffer>();
	}

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request, final PersonalProfile profile) {
		return Collections2.filter(offers.values(), new Predicate<PublishedRoomOffer>() {

			@Override
			public boolean apply(PublishedRoomOffer input) {
//				return request.apply(input.getOffer()) && input.getOffer().matches(profile);
				return true;
			}

		});
	}

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request) {
		return Collections2.filter(offers.values(), new Predicate<PublishedRoomOffer>() {

			@Override
			public boolean apply(PublishedRoomOffer input) {
//				return request.apply(input.getOffer());
				return true;
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
