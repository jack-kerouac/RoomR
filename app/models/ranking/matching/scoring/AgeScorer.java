package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.collect.Range;

public final class AgeScorer extends AbstractAttributeScorer<Integer> {

	private final int MAX_YEARS_TOLERANCE = 5;

	@Override
	public Score score(RoomOffer offer, Integer seekerAge) {
		Range<Integer> ageRange = offer.criteria.getAgeRange();
		if (ageRange.contains(seekerAge))
			return Score.MAX;

		int yearsDifference;
		if (seekerAge < ageRange.lowerEndpoint()) {
			// the seeker is too young
			yearsDifference = ageRange.lowerEndpoint() - seekerAge;
		} else {
			// the seeker is too old
			yearsDifference = seekerAge - ageRange.upperEndpoint();
		}

		if (yearsDifference > MAX_YEARS_TOLERANCE)
			return Score.MIN;

		return Score.defined(Score.MAX.getValue() - yearsDifference
				/ (double) MAX_YEARS_TOLERANCE);
	}

}
