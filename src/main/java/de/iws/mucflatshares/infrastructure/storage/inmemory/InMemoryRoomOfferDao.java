package de.iws.mucflatshares.infrastructure.storage.inmemory;

import java.util.Collection;

import de.iws.mucflatshares.domain.offering.offer.RoomOffer;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;
import de.iws.mucflatshares.infrastructure.storage.RoomOfferDao;

public class InMemoryRoomOfferDao implements RoomOfferDao {

	@Override
	public Collection<RoomOffer> findByRequest(RoomFilter request) {
		// TODO Auto-generated method stub
		return null;
	}

}
