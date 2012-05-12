package facade;

import java.util.Set;

import models.offer.RoomOffer;

import com.google.common.collect.Sets;

public class AdministrationFacade {

	public Set<RoomOffer> findAllOffers() {
		return Sets.newHashSet(RoomOffer.all());
	}

}
