package de.iws.livingroom.domain.offering.offer;

import de.iws.livingroom.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface RoomOfferRepository {

	public void add(RoomOffer offer);

	public RoomOffer findById(long offerId);

}
