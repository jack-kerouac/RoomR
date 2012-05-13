package models.ranking.matching.scoring;

import models.common.Score;
import models.offer.RoomOffer;
import models.request.RoomRequest.DateQuery;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import com.google.common.base.Optional;

public class StartDateScorer extends AbstractAttributeScorer<DateQuery> {

	@Override
	public Score score(RoomOffer offer, DateQuery startDateQuery) {
		DateTime roomFreeFrom = new DateTime(offer.roomDetails.freeFrom);
		Optional<DateTime> roomFreeTo = offer.roomDetails.freeTo == null ? Optional.<DateTime> absent() : Optional
				.of(new DateTime(offer.roomDetails.freeTo));

		DateTime desiredMovingInDate = new DateTime(startDateQuery.getDate());

		if (roomFreeFrom.isBefore(desiredMovingInDate)) {
			// if the room is free in the past, it is still free now, otherwise,
			// the offer would have been removed.
			// but only, if a potentially defined end date is not in the past
			if (roomFreeTo.isPresent() && roomFreeTo.get().isBefore(desiredMovingInDate))
				return Score.MIN;
			else
				return Score.MAX;
		} else {
			Duration difference = new Interval(desiredMovingInDate, roomFreeFrom).toDuration();
			if (difference.isShorterThan(Duration.standardDays(14)))
				return Score.MAX;
			else if (difference.isShorterThan(Duration.standardDays(42))) {
				// TODO (Flo): calculate difference here:
				int daysOver = 14;
				return Score.defined(-Score.MAX.getValue() / 28.0 * daysOver + Score.MAX.getValue());
			} else
				return Score.MIN;
		}
	}
}
