package de.iws.mucflatshares.domain.market;

import java.util.Collection;

import de.iws.mucflatshares.domain.common.PersonalProfile;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;
import de.iws.mucflatshares.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface PublishedRoomOfferRepository {

	public Collection<PublishedRoomOffer> findAllThatMatch(final RoomFilter request, final PersonalProfile profile);

	public Collection<PublishedRoomOffer> findAllThatMatch(final RoomFilter request);

	public void add(PublishedRoomOffer offer);

	public PublishedRoomOffer findById(long offerId);

}
