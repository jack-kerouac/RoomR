package de.iws.livingroom.domain.market;

import java.util.Collection;

import de.iws.livingroom.domain.common.PersonalProfile;
import de.iws.livingroom.domain.seeking.request.FlatshareRequest;
import de.iws.livingroom.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface PublishedRoomOfferRepository {

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request, final PersonalProfile profile);

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request);

	public void add(PublishedRoomOffer offer);

	public PublishedRoomOffer findById(long offerId);

}
