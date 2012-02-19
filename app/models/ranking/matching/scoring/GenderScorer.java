package models.ranking.matching.scoring;

import models.common.Gender;
import models.common.Score;
import models.offer.RoomOffer;

import com.google.common.base.Optional;

public final class GenderScorer implements AttributeScorer<Gender> {

	@Override
	public Score score(RoomOffer offer, Optional<Gender> optionalSeekerGender) {
		if (!optionalSeekerGender.isPresent()) {
			return Score.undefined();
		}
		Gender seekerGender = optionalSeekerGender.get();
		
		if (offer.criteria.genders.contains(seekerGender))
			return Score.MAX;
		else
			return Score.MIN;
	}
	
}
