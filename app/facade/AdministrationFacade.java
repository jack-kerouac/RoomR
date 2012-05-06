package facade;

import java.util.Set;

import javax.inject.Inject;

import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;

public class AdministrationFacade {

	@Inject
	private RoomOfferRepository offerRepository;

	public Set<RoomOffer> findAllOffers() {
		return offerRepository.findAll();
	}

}
