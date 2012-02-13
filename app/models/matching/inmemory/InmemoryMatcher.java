package models.matching.inmemory;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import models.common.Age;
import models.common.Gender;
import models.common.Score;
import models.matching.OfferMatcher;
import models.matching.RankedRoomOffer;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.request.RoomRequest;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;

public class InmemoryMatcher implements OfferMatcher {

	public final static double SCORE_FOR_ABSENT_SEEKER_PROPERTIES = 0.95;

	@Inject
	private RoomOfferRepository offerRepository;

	@Override
	public List<RankedRoomOffer> match(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
		Set<RoomOffer> allOffers = offerRepository.findAll();

		Iterable<RankedRoomOffer> rankedRoomOffers = Iterables.transform(allOffers, new RankOfferFunction(request,
				seekerAge, seekerGender));
		// TODO: use compound ordering to order by other attributes, like date of last modification
		List<RankedRoomOffer> orderedAndRankedRoomOffers = Ordering.natural().reverse().sortedCopy(rankedRoomOffers);

		return Lists.newArrayList(Iterables.limit(orderedAndRankedRoomOffers, 10));
	}

	private class RankOfferFunction implements Function<RoomOffer, RankedRoomOffer> {

		private final RoomRequest request;
		private final Optional<Age> seekerAge;
		private final Optional<Gender> seekerGender;

		public RankOfferFunction(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
			this.request = request;
			this.seekerAge = seekerAge;
			this.seekerGender = seekerGender;
		}

		private final double AGE_RANK_WEIGHT = 0.5;
		private final double GENDER_RANK_WEIGHT = 0.5;

		@Override
		public RankedRoomOffer apply(RoomOffer offer) {
			// Money rent = offer.roomDetails.getTotalRentPerMonth();
			// return (offer.flatshare.floor == floor) &&
			// (rent.isLessThan(maxRent) ||
			// rent.isEqual(maxRent));

			boolean scoreDefined = false;
			double totalWeight = 0.0, totalScore = 0.0;
			if (seekerAge.isPresent()) {
				totalWeight += AGE_RANK_WEIGHT;
				totalScore += AGE_RANK_WEIGHT * matchAge(offer, seekerAge.get());
				scoreDefined = true;
			}
			if (seekerGender.isPresent()) {
				totalWeight += GENDER_RANK_WEIGHT;
				totalScore += GENDER_RANK_WEIGHT * matchGender(offer, seekerGender.get());
				scoreDefined = true;
			}

			if (scoreDefined)
				return new RankedRoomOffer(offer, Score.defined(totalScore / totalWeight));
			else
				return new RankedRoomOffer(offer, Score.undefined());
		}

		private final int MAX_YEARS_TOLERANCE = 5;

		private double matchAge(RoomOffer offer, Age seekerAge) {
			Range<Age> ageRange = offer.criteria.getAgeRange();
			if (ageRange.contains(seekerAge))
				return 1.0;

			int yearsDifference;
			if (seekerAge.isLessThan(ageRange.lowerEndpoint())) {
				// the seeker is too young
				yearsDifference = ageRange.lowerEndpoint().years - seekerAge.years;
			} else {
				// the seeker is too old
				yearsDifference = seekerAge.years - ageRange.upperEndpoint().years;
			}

			if (yearsDifference > MAX_YEARS_TOLERANCE)
				return 0.0;

			return 1.0 - yearsDifference / (double) MAX_YEARS_TOLERANCE;
		}

		private double matchGender(RoomOffer offer, Gender seekerGender) {
			if (offer.criteria.genders.contains(seekerGender))
				return 1.0;
			else
				return 0.0;
		}

	}

}
