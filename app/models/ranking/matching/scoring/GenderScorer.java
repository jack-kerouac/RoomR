package models.ranking.matching.scoring;

import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;

public final class GenderScorer implements AttributeScorer<Gender> {

	@Override
	public Score score(RoomOffer offer, Gender seekerGender) {
		if (offer.criteria.genders.contains(seekerGender))
			return Score.MAX;
		else
			return Score.MIN;
	}
	
}
