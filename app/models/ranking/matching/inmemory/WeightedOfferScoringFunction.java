package models.ranking.matching.inmemory;

import models.common.Age;
import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;
import models.ranking.matching.ScoredRoomOffer;
import models.ranking.matching.scoring.AgeScorer;
import models.ranking.matching.scoring.GenderScorer;
import models.request.RoomRequest;

import com.google.common.base.Optional;

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
	private final double GENDER_RANK_WEIGHT = 0.5;

	@Override
	public ScoredRoomOffer apply(RoomOffer offer) {
		boolean scoreDefined = false;
		double totalWeight = 0.0, totalScore = 0.0;
		if (seekerAge.isPresent()) {
			totalWeight += AGE_RANK_WEIGHT;
			totalScore += AGE_RANK_WEIGHT * new AgeScorer().score(offer, seekerAge.get()).getValue();
			scoreDefined = true;
		}
		if (seekerGender.isPresent()) {
			totalWeight += GENDER_RANK_WEIGHT;
			totalScore += GENDER_RANK_WEIGHT * new GenderScorer().score(offer, seekerGender.get()).getValue();
			scoreDefined = true;
		}

		// TODO: extend matching by including request.
		
		if (scoreDefined)
			return new ScoredRoomOffer(offer, Score.defined(totalScore / totalWeight));
		else
			return new ScoredRoomOffer(offer, Score.undefined());
	}

}