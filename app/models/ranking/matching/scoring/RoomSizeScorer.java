package models.ranking.matching.scoring;

import models.common.FloorSpace;
import models.common.Score;
import models.offer.RoomOffer;

public class RoomSizeScorer extends AbstractAttributeScorer<FloorSpace> {

	private final double MAX_PERCENT_UNDER_MINFLOORSPACE = 0.25;

	
	@Override
	public Score score(RoomOffer offer, FloorSpace minFloorSpace) {
		FloorSpace roomSize = offer.roomDetails.roomSize;
		
		if(roomSize.isAtLeast(minFloorSpace))
			return Score.MAX;
		
		double percentageUnderMin = 1.0 - roomSize.squareMeters / minFloorSpace.squareMeters;
		
		if(percentageUnderMin > MAX_PERCENT_UNDER_MINFLOORSPACE)
			return Score.MIN;
		
		return Score.defined(- 1.0 / MAX_PERCENT_UNDER_MINFLOORSPACE * percentageUnderMin + 1.0);
	}

	
}
