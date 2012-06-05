package models.ranking.matching.inmemory;

import java.util.List;
import java.util.Set;

import models.common.Gender;
import models.offer.RoomOffer;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class InmemoryMatcher implements OfferMatcher {

	public final static double SCORE_FOR_ABSENT_SEEKER_PROPERTIES = 0.95;

	@Override
	public Set<ScoredRoomOffer> match(RoomRequest request,
			Optional<Integer> seekerAge, Optional<Gender> seekerGender) {
		// just retrieve all room offers and sort them in memory
		List<RoomOffer> allOffers = RoomOffer.all().fetch();

		Iterable<ScoredRoomOffer> scoredRoomOffers = Iterables.transform(
				allOffers, new WeightedOfferScoringFunction(request, seekerAge,
						seekerGender));

		return ImmutableSet.copyOf(scoredRoomOffers);
	}

}
