package models.ranking.matching;

import java.util.Set;

import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Objects;

public class ScoredRoomOffer implements Comparable<ScoredRoomOffer> {

	public RoomOffer offer;

	// / 0.0 .. 1.0
	public Score matchingScore;

	public Set<MatchingCriterion> unmetCriteria;
	
	@Override
	public int compareTo(ScoredRoomOffer o) {
		return this.matchingScore.compareTo(o.matchingScore);
	}

	public ScoredRoomOffer(RoomOffer offer, Score matchingScore, Set<MatchingCriterion> unmetCriteria) {
		this.offer = offer;
		this.matchingScore = matchingScore;
		this.unmetCriteria = unmetCriteria;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("score", matchingScore).add("offer", offer).toString();
	}

}
