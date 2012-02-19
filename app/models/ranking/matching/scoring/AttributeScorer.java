package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Optional;

public interface AttributeScorer<AttributeType> {

	public Score score(RoomOffer offer, Optional<AttributeType> attribute);
	
}
