package models.market;

import java.util.Collection;

import models.annotations.ddd.DomainRepository;
import models.common.PersonalProfile;
import models.seeking.request.FlatshareRequest;

@DomainRepository
public interface PublishedRoomOfferRepository {

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request, final PersonalProfile profile);

	public Collection<PublishedRoomOffer> findAllThatMatch(final FlatshareRequest request);

	public void add(PublishedRoomOffer offer);

	public PublishedRoomOffer findById(long offerId);

}
