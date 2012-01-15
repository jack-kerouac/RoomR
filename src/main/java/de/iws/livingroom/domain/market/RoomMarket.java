package de.iws.livingroom.domain.market;

import java.util.Collection;

import org.joda.time.DateTime;

import de.iws.livingroom.domain.common.PersonalProfile;
import de.iws.livingroom.domain.offering.offer.RoomOffer;
import de.iws.livingroom.domain.seeking.request.FlatshareRequest;
import de.iws.livingroom.technical.annotations.ddd.DomainService;

@DomainService
public final class RoomMarket {

	private PublishedRoomOfferRepository offerRepos;

	public PublishedRoomOffer offerRoom(RoomOffer offer) {
		PublishedRoomOffer roomOfferOnMarket = new PublishedRoomOffer(offer, new DateTime());
		offerRepos.add(roomOfferOnMarket);
		
		return roomOfferOnMarket;
	}

	public Collection<PublishedRoomOffer> retrieveCurrentOffers(FlatshareRequest request, PersonalProfile profile) {
		return offerRepos.findAllThatMatch(request, profile);
	}

	public Collection<PublishedRoomOffer> retrieveCurrentOffers(FlatshareRequest request) {
		return offerRepos.findAllThatMatch(request);
	}
	
}
