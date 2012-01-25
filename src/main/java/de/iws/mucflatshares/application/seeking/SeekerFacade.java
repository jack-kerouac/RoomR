package de.iws.mucflatshares.application.seeking;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.iws.mucflatshares.domain.market.PublishedRoomOffer;
import de.iws.mucflatshares.domain.market.RoomMarket;
import de.iws.mucflatshares.domain.seeking.RoomSeeker;
import de.iws.mucflatshares.domain.seeking.RoomSeekerRepository;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

@Service
public class SeekerFacade {

	@Autowired
	private RoomSeekerRepository seekerRepository;

	@Autowired
	private RoomMarket market;

	@Transactional
	public Collection<PublishedRoomOffer> retrieveOffers(RoomFilter request) {
		return market.retrieveCurrentOffers(request);
	}

	@Transactional
	public Collection<PublishedRoomOffer> retrieveOffersForSeeker(String seekerId, RoomFilter request) {
		RoomSeeker seeker = seekerRepository.findForId(seekerId);

		return market.retrieveCurrentOffers(request, seeker.getProfile());
	}

}
