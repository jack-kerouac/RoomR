package facade;

import java.util.List;
import java.util.Set;

import models.offer.RoomOffer;

import com.google.common.collect.ImmutableSet;

public class AdministrationFacade {

	public Set<RoomOffer> findAllOffers() {
		List allOffers = RoomOffer.all().fetch();
		return ImmutableSet.copyOf(allOffers);
	}

}
