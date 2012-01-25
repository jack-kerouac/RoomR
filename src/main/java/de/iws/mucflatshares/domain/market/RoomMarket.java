package de.iws.mucflatshares.domain.market;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;
import de.iws.mucflatshares.technical.annotations.ddd.DomainService;

@DomainService
public final class RoomMarket {

	@Autowired
	private PublishedRoomOfferRepository offerRepos;

	public PublishedRoomOffer offerRoom(RoomOffer offer) {
		PublishedRoomOffer roomOfferOnMarket = new PublishedRoomOffer(offer, new DateTime());
		offerRepos.add(roomOfferOnMarket);
		
		return roomOfferOnMarket;
	}

	public Collection<PublishedRoomOffer> retrieveCurrentOffers(RoomFilter request, PersonalProfile profile) {
		return offerRepos.findAllThatMatch(request, profile);
	}

	public Collection<PublishedRoomOffer> retrieveCurrentOffers(RoomFilter request) {
		return offerRepos.findAllThatMatch(request);
	}
	
}
