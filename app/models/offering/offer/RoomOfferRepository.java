package models.offering.offer;

import models.annotations.ddd.DomainRepository;

@DomainRepository
public interface RoomOfferRepository {

	public void add(RoomOffer offer);

	public RoomOffer findById(long offerId);

}
