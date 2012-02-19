package models.ranking.matching.inmemory;

import java.util.Set;

import models.common.Age;
import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;
import models.ranking.matching.MatchingCriterion;
import models.ranking.matching.ScoredRoomOffer;
import models.ranking.matching.scoring.AgeScorer;
import models.ranking.matching.scoring.GenderScorer;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

public class WeightedOfferScoringFunction implements OfferScoringFunction {

	private final RoomRequest request;
	private final Optional<Age> seekerAge;
	private final Optional<Gender> seekerGender;

	public WeightedOfferScoringFunction(RoomRequest request, Optional<Age> seekerAge, Optional<Gender> seekerGender) {
		this.request = request;
		this.seekerAge = seekerAge;
		this.seekerGender = seekerGender;
	}

	private final double AGE_RANK_WEIGHT = 0.5;
	private final double GENDER_RANK_WEIGHT = 1.5;

	@Override
	public ScoredRoomOffer apply(RoomOffer offer) {
		double totalWeight = 0.0, totalScore = 0.0;

		Set<MatchingCriterion> unmetCriteria = Sets.newLinkedHashSet();

		Score ageScore = new AgeScorer().score(offer, seekerAge);
		if (ageScore.isDefined()) {
			if (!ageScore.isMax())
				unmetCriteria.add(MatchingCriterion.AGE);
			totalWeight += AGE_RANK_WEIGHT;
			totalScore += AGE_RANK_WEIGHT * ageScore.getValue();
		}

		Score genderScore = new GenderScorer().score(offer, seekerGender);
		if (genderScore.isDefined()) {
			if (!genderScore.isMax())
				unmetCriteria.add(MatchingCriterion.GENDER);
			totalWeight += GENDER_RANK_WEIGHT;
			totalScore += GENDER_RANK_WEIGHT * genderScore.getValue();
		}
		
		if (totalWeight != 0.0)
			return new ScoredRoomOffer(offer, Score.defined(totalScore / totalWeight), unmetCriteria);
		else
			return new ScoredRoomOffer(offer, Score.undefined(), unmetCriteria);
	}

}