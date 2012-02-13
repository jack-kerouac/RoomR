package models.matching.inmemory;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import models.common.Age;
import models.common.Gender;
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

	@Inject
	private RoomOfferRepository offerRepository;


	@Override
	public List<RankedRoomOffer> match(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
		Set<RoomOffer> allOffers = offerRepository.findAll();

		Iterable<RankedRoomOffer> rankedRoomOffers = Iterables.transform(allOffers, new RankOfferFunction(request,
				seekerAge, seekerGender));
		List<RankedRoomOffer> orderedAndRankedRoomOffers = Ordering.natural().sortedCopy(rankedRoomOffers);

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

		
		private final double AGE_RANK_WEIGHT = 1.0;
		
		@Override
		public RankedRoomOffer apply(RoomOffer offer) {
			// Money rent = offer.roomDetails.getTotalRentPerMonth();
			// return (offer.flatshare.floor == floor) && (rent.isLessThan(maxRent) ||
			// rent.isEqual(maxRent));

			if(seekerAge.isPresent())
				return new RankedRoomOffer(offer, AGE_RANK_WEIGHT * ageMatch(offer, seekerAge.get()));
			else
				return new RankedRoomOffer(offer, AGE_RANK_WEIGHT * 1.0);
		}


		private final int MAX_YEARS_TOLERANCE = 5;
		
		private double ageMatch(RoomOffer offer, Age seekerAge) {
			Range<Age> ageRange = offer.criteria.getAgeRange();
			if (ageRange.contains(seekerAge))
				return 1.0;

			int yearsDifference;
			if (seekerAge.isLessThan(ageRange.lowerEndpoint())) {
				// the seeker is too young
				yearsDifference = ageRange.lowerEndpoint().years - seekerAge.years;
			}
			else {
				// the seeker is too old
				yearsDifference = seekerAge.years - ageRange.upperEndpoint().years;
			}
			
			if(yearsDifference > MAX_YEARS_TOLERANCE)
				return 0.0;
			
			return 1.0 - yearsDifference / MAX_YEARS_TOLERANCE;
		}

	}

}
