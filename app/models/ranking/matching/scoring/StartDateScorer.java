package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;
import models.request.RoomRequest.DateQuery;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class StartDateScorer extends AbstractAttributeScorer<DateQuery> {

	@Override
	public Score score(RoomOffer offer, DateQuery startDateQuery) {
		DateTime roomFreeFrom = new DateTime(offer.roomDetails.freeFrom);

		DateTime desiredMovingInDate = new DateTime(startDateQuery.getDate());

		if (roomFreeFrom.isBefore(desiredMovingInDate))
			return Score.MAX;
		else {
			Duration difference = new Interval(desiredMovingInDate, roomFreeFrom).toDuration();
			if (difference.isShorterThan(Duration.standardDays(14)))
				return Score.MAX;
			else if (difference.isShorterThan(Duration.standardDays(42))) {
				// TODO (Flo): calculate difference here:
				int daysOver = 14;
				return Score.defined(-Score.MAX.getValue() / 28.0 * daysOver + Score.MAX.getValue());
			}
			else
				return Score.MIN;
		}
	}
}
