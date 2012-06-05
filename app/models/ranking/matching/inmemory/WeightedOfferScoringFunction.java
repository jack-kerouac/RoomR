package models.ranking.matching.inmemory;

import static models.ranking.matching.MatchingCriterion.AGE;
import static models.ranking.matching.MatchingCriterion.CITY;
import static models.ranking.matching.MatchingCriterion.GENDER;
import static models.ranking.matching.MatchingCriterion.RENT_PER_MONTH;
import static models.ranking.matching.MatchingCriterion.ROOM_SIZE;
import static models.ranking.matching.MatchingCriterion.START_DATE;

import java.util.Map;
import java.util.Set;

import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;
import models.ranking.matching.MatchingCriterion;
import models.ranking.matching.ScoredRoomOffer;
import models.ranking.matching.scoring.AgeScorer;
import models.ranking.matching.scoring.GenderScorer;
import models.ranking.matching.scoring.RentPerMonthScorer;
import models.ranking.matching.scoring.RoomSizeScorer;
import models.ranking.matching.scoring.StartDateScorer;
import models.request.RoomRequest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class WeightedOfferScoringFunction implements OfferScoringFunction {

	private final RoomRequest request;
	private final Optional<Integer> seekerAge;
	private final Optional<Gender> seekerGender;

	public WeightedOfferScoringFunction(RoomRequest request,
			Optional<Integer> seekerAge, Optional<Gender> seekerGender) {
		this.request = request;
		this.seekerAge = seekerAge;
		this.seekerGender = seekerGender;
	}

	// @formatter:off
	private final Map<MatchingCriterion, Double> weights = ImmutableMap
			.<MatchingCriterion, Double> builder().put(AGE, 0.5)
			.put(GENDER, 1.5).put(CITY, 2.0).put(ROOM_SIZE, 0.5)
			.put(RENT_PER_MONTH, 1.0).put(START_DATE, 1.0).build();

	// @formatter:on

	double totalWeight = 0.0, totalScore = 0.0;

	Set<MatchingCriterion> metCriteria = Sets.newLinkedHashSet();
	Set<MatchingCriterion> unmetCriteria = Sets.newLinkedHashSet();
	Set<MatchingCriterion> undefinedCriteria = Sets.newLinkedHashSet();

	/**
	 * This method is not thread safe!
	 */
	@Override
	public ScoredRoomOffer apply(RoomOffer offer) {
		evaluateScore(new AgeScorer().score(offer, seekerAge), AGE);
		evaluateScore(new GenderScorer().score(offer, seekerGender), GENDER);

		// TODO: remove city scorer and just load offers from one city!
		// evaluateScore(new CityScorer().score(offer, request.getCity()),
		// CITY);
		evaluateScore(
				new RoomSizeScorer().score(offer, request.getMinRoomSize()),
				ROOM_SIZE);
		evaluateScore(
				new RentPerMonthScorer().score(offer, request.getMaxRent()),
				RENT_PER_MONTH);
		evaluateScore(
				new StartDateScorer().score(offer, request.getStartDateQuery()),
				START_DATE);

		ScoredRoomOffer scoredRoomOffer;
		if (totalWeight != 0.0)
			scoredRoomOffer = new ScoredRoomOffer(offer,
					Score.defined(totalScore / totalWeight), metCriteria,
					unmetCriteria, undefinedCriteria);
		else
			scoredRoomOffer = new ScoredRoomOffer(offer, Score.undefined(),
					metCriteria, unmetCriteria, undefinedCriteria);

		totalWeight = 0.0;
		totalScore = 0.0;
		metCriteria = Sets.newLinkedHashSet();
		unmetCriteria = Sets.newLinkedHashSet();
		undefinedCriteria = Sets.newLinkedHashSet();

		return scoredRoomOffer;
	}

	private void evaluateScore(Score score, MatchingCriterion criterion) {
		if (score.isDefined()) {
			if (score.isMax())
				metCriteria.add(criterion);
			else
				unmetCriteria.add(criterion);
			totalWeight += weights.get(criterion);
			totalScore += weights.get(criterion) * score.getValue();
		} else {
			undefinedCriteria.add(criterion);
		}
	}
}