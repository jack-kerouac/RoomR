package de.iws.mucflatshares.domain.offering.offer;

import de.iws.mucflatshares.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface RoomOfferRepository {

	public void add(RoomOffer offer);

	public RoomOffer findById(long offerId);

}
