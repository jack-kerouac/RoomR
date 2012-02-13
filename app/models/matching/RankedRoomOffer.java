package models.matching;

import models.offer.RoomOffer;

import com.google.common.base.Objects;

public class RankedRoomOffer implements Comparable<RankedRoomOffer> {

	public RoomOffer offer;

	// / 0.0 .. 1.0
	public double matchingScore;

	@Override
	public int compareTo(RankedRoomOffer o) {
		return Double.compare(o.matchingScore, this.matchingScore);
	}

	public RankedRoomOffer(RoomOffer offer, double matchingScore) {
		this.offer = offer;
		this.matchingScore = matchingScore;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("score: ", matchingScore).add("offer", offer).toString();
	}

}
