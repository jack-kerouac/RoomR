package de.iws.livingroom.application;

import com.google.inject.Inject;

import de.iws.livingroom.domain.seeking.FlatshareSeeker;
import de.iws.livingroom.domain.seeking.FlatshareSeekerRepository;

public class SeekerFacade {
	
	@Inject
	private FlatshareSeekerRepository seekerRepository;

//	@Transactional
//	public Collection<PublishedRoomOffer> retrieveOffers(FlatshareRequest request) {
//		return market.retrieveCurrentOffers(request);
//	}
//
//	@Transactional
//	public Collection<PublishedRoomOffer> retrieveOffersForSeeker(String seekerId, FlatshareRequest request) {
//		FlatshareSeeker seeker = seekerRepository.findForId(seekerId);
//
//		return market.retrieveCurrentOffers(request, seeker.getProfile());
//	}

	public void createSeeker(FlatshareSeeker seeker) {
		seekerRepository.add(seeker);
	}

}
