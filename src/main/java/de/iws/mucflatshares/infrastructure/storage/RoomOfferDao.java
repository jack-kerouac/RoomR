package de.iws.mucflatshares.infrastructure.storage;

import java.util.Collection;

import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

public interface RoomOfferDao {

	public Collection<RoomOffer> findByRequest(RoomFilter request);
	
}
