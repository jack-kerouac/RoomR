package models.market;

import java.util.Collection;

import models.annotations.ddd.DomainService;
import models.common.PersonalProfile;
import models.offering.offer.RoomOffer;
import models.seeking.request.FlatshareRequest;

import org.joda.time.DateTime;

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
