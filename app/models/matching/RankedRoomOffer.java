package models.matching;

import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Objects;

public class RankedRoomOffer implements Comparable<RankedRoomOffer> {

	public RoomOffer offer;

	// / 0.0 .. 1.0
	public Score matchingScore;

	@Override
	public int compareTo(RankedRoomOffer o) {
		return this.matchingScore.compareTo(o.matchingScore);
	}

	public RankedRoomOffer(RoomOffer offer, Score matchingScore) {
		this.offer = offer;
		this.matchingScore = matchingScore;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("score", matchingScore).add("offer", offer).toString();
	}

}
