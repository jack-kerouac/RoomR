package models.ranking;

import java.util.List;
import java.util.Set;

import models.common.Gender;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.ScoredRoomOffer;
import models.ranking.sorting.OfferSorter;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.inject.Inject;

public class OfferRanker {

	private final OfferMatcher matcher;
	private final OfferSorter sorter;

	@Inject
	public OfferRanker(OfferMatcher matcher, OfferSorter sorter) {
		this.matcher = matcher;
		this.sorter = sorter;
	}

	public List<ScoredRoomOffer> search(RoomRequest request,
			Optional<Integer> seekerAge, Optional<Gender> seekerGender) {
		Set<ScoredRoomOffer> scoredOffers = matcher.match(request, seekerAge,
				seekerGender);

		return sorter.sort(scoredOffers);
	}

}
