package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;

import org.joda.money.Money;

public class RentPerMonthScorer extends AbstractAttributeScorer<Money> {

	private final double MAX_PERCENT_OVER_MAXRENT = 0.4;
	
	@Override
	public Score score(RoomOffer offer, Money maxRentPerMonth) {
		Money rentPerMont = offer.roomDetails.getTotalRentPerMonth();
		
		if(rentPerMont.isLessThan(maxRentPerMonth) || rentPerMont.isEqual(maxRentPerMonth))
			return Score.MAX;
		
		double percentOverRent = (double) rentPerMont.getAmountMinorInt() / maxRentPerMonth.getAmountMinorInt() - 1.0;
		
		if(percentOverRent > MAX_PERCENT_OVER_MAXRENT)
			return Score.MIN;
		
		return Score.defined(- 1.0 / MAX_PERCENT_OVER_MAXRENT * percentOverRent + 1.0);
	}

}
