package models.ranking.matching.inmemory;

import static models.ranking.matching.MatchingCriterion.AGE;
import static models.ranking.matching.MatchingCriterion.CITY;
import static models.ranking.matching.MatchingCriterion.GENDER;
import static models.ranking.matching.MatchingCriterion.RENT_PER_MONTH;
import static models.ranking.matching.MatchingCriterion.ROOM_SIZE;

import java.util.Map;
import java.util.Set;

import models.common.Age;
import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;
import models.ranking.matching.MatchingCriterion;
import models.ranking.matching.ScoredRoomOffer;
import models.ranking.matching.scoring.AgeScorer;
import models.ranking.matching.scoring.CityScorer;
import models.ranking.matching.scoring.GenderScorer;
import models.ranking.matching.scoring.RentPerMonthScorer;
import models.ranking.matching.scoring.RoomSizeScorer;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
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

	// @formatter:off
	private final Map<MatchingCriterion, Double> weights = ImmutableMap.<MatchingCriterion, Double> builder().
			put(AGE, 0.5).
			put(GENDER, 1.5).
			put(CITY, 2.0).
			put(ROOM_SIZE, 0.5).
			put(RENT_PER_MONTH, 1.0).
		build();

	// @formatter:on

	@Override
	public ScoredRoomOffer apply(RoomOffer offer) {
		double totalWeight = 0.0, totalScore = 0.0;

		Set<MatchingCriterion> unmetCriteria = Sets.newLinkedHashSet();

		// AGE
		{
			Score ageScore = new AgeScorer().score(offer, seekerAge);
			if (ageScore.isDefined()) {
				if (!ageScore.isMax())
					unmetCriteria.add(AGE);
				totalWeight += weights.get(AGE);
				totalScore += weights.get(AGE) * ageScore.getValue();
			}
		}

		// GENDER
		{
			Score genderScore = new GenderScorer().score(offer, seekerGender);
			if (genderScore.isDefined()) {
				if (!genderScore.isMax())
					unmetCriteria.add(GENDER);
				totalWeight += weights.get(GENDER);
				totalScore += weights.get(GENDER) * genderScore.getValue();
			}
		}

		// CITY
		{
			Score cityScore = new CityScorer().score(offer, request.getCity());
			if (cityScore.isDefined()) {
				if (!cityScore.isMax())
					unmetCriteria.add(CITY);
				totalWeight += weights.get(CITY);
				totalScore += weights.get(CITY) * cityScore.getValue();
			}
		}
		
		// MIN ROOM SIZE
		{
			Score roomSizeScore = new RoomSizeScorer().score(offer, request.getMinRoomSize());
			if (roomSizeScore.isDefined()) {
				if (!roomSizeScore.isMax())
					unmetCriteria.add(ROOM_SIZE);
				totalWeight += weights.get(ROOM_SIZE);
				totalScore += weights.get(ROOM_SIZE) * roomSizeScore.getValue();
			}
		}
		
		// MAX RENT PER MONTH
		{
			Score rentPerMonthScore = new RentPerMonthScorer().score(offer, request.getMaxRent());
			if (rentPerMonthScore.isDefined()) {
				if (!rentPerMonthScore.isMax())
					unmetCriteria.add(RENT_PER_MONTH);
				totalWeight += weights.get(RENT_PER_MONTH);
				totalScore += weights.get(RENT_PER_MONTH) * rentPerMonthScore.getValue();
			}
		}
		
		if (totalWeight != 0.0)
			return new ScoredRoomOffer(offer, Score.defined(totalScore / totalWeight), unmetCriteria);
		else
			return new ScoredRoomOffer(offer, Score.undefined(), unmetCriteria);
	}
}