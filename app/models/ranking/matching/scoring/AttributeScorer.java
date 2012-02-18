package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

public interface AttributeScorer<AttributeType> {

	public Score score(RoomOffer offer, AttributeType attribute);
	
}
