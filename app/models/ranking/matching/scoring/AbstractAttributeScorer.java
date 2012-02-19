package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Optional;

public abstract class AbstractAttributeScorer<AttributeType> implements AttributeScorer<AttributeType> {

	@Override
	public final Score score(RoomOffer offer, Optional<AttributeType> optionalAttribute) {
		if(!optionalAttribute.isPresent())
			return Score.undefined();
		
		return score(offer, optionalAttribute.get());
	}

	protected abstract Score score(RoomOffer offer, AttributeType attribute);
	
}
