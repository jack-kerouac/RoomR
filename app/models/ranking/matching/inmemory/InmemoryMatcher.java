package models.ranking.matching.inmemory;

import java.util.Set;

import javax.inject.Inject;

import models.common.Age;
import models.common.Gender;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class InmemoryMatcher implements OfferMatcher {

	public final static double SCORE_FOR_ABSENT_SEEKER_PROPERTIES = 0.95;

	@Inject
	private RoomOfferRepository offerRepository;

	@Override
	public Set<ScoredRoomOffer> match(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
		// just retrieve all room offers and sort them in memory
		Set<RoomOffer> allOffers = offerRepository.findAll();

		Iterable<ScoredRoomOffer> scoredRoomOffers = Iterables.transform(allOffers, new WeightedOfferScoringFunction(request,
				seekerAge, seekerGender));
		
		return ImmutableSet.copyOf(scoredRoomOffers);
	}

}
