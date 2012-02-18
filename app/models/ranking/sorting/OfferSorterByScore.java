package models.ranking.sorting;

import java.util.List;
import java.util.Set;

import models.ranking.matching.ScoredRoomOffer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class OfferSorterByScore implements OfferSorter {

	@Override
	public List<ScoredRoomOffer> sort(Set<ScoredRoomOffer> scoredOffers) {
		// TODO: use compound ordering to order by other attributes, like date of last modification
		List<ScoredRoomOffer> orderedAndRankedRoomOffers = Ordering.natural().reverse().sortedCopy(scoredOffers);

		return Lists.newArrayList(Iterables.limit(orderedAndRankedRoomOffers, 10));
	}
	
}
