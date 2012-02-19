package models.ranking.matching.scoring;

import models.common.Age;
import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Optional;
import com.google.common.collect.Range;

public final class AgeScorer implements AttributeScorer<Age> {

	private final int MAX_YEARS_TOLERANCE = 5;

	@Override
	public Score score(RoomOffer offer, Optional<Age> optionalSeekerAge) {
		if (!optionalSeekerAge.isPresent()) {
			return Score.undefined();
		}
		Age seekerAge = optionalSeekerAge.get();
		
		Range<Age> ageRange = offer.criteria.getAgeRange();
		if (ageRange.contains(seekerAge))
			return Score.MAX;

		int yearsDifference;
		if (seekerAge.isLessThan(ageRange.lowerEndpoint())) {
			// the seeker is too young
			yearsDifference = ageRange.lowerEndpoint().years - seekerAge.years;
		} else {
			// the seeker is too old
			yearsDifference = seekerAge.years - ageRange.upperEndpoint().years;
		}

		if (yearsDifference > MAX_YEARS_TOLERANCE)
			return Score.MIN;

		return Score.defined(Score.MAX.getValue() - yearsDifference / (double) MAX_YEARS_TOLERANCE);
	}


}
