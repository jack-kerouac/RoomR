package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

public class CityScorer extends AbstractAttributeScorer<String> {

	@Override
	public Score score(RoomOffer offer, String city) {
		if (!offer.flatshare.address.city.toLowerCase().startsWith(
				city.toLowerCase()))
			return Score.MIN;
		else
			return Score.MAX;
	}

}
