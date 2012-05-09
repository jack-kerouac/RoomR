package models.request;

import java.util.Date;

import models.common.FloorSpace;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.common.base.Optional;

public class RoomRequest {

	public static class DateQuery {
		private boolean now;
		private Date date;

		private DateQuery() {}

		public static DateQuery now() {
			DateQuery dq = new DateQuery();
			dq.now = true;
			return dq;
		}

		public static DateQuery fixedDate(Date date) {
			DateQuery dq = new DateQuery();
			dq.now = false;
			dq.date = date;
			return dq;
		}

		public boolean isNow() {
			return now;
		}

		/**
		 * if type is "now", returns {@code new Date()}, else, returns fixed date.
		 */
		public Date getDate() {
			return isNow() ? new Date() : date;
		}

	}

	public String city;
	public FloorSpace minRoomSize;
	public Double maxRentPerMonthInEuro;


	public DateQuery startDateQuery;

	public Optional<DateQuery> getStartDateQuery() {
		return Optional.fromNullable(startDateQuery);
	}

	public Optional<FloorSpace> getMinRoomSize() {
		if (minRoomSize == null || minRoomSize.squareMeters == null)
			return Optional.<FloorSpace> absent();
		else
			return Optional.<FloorSpace> of(minRoomSize);
	}

	public Optional<Money> getMaxRent() {
		if (maxRentPerMonthInEuro == null)
			return Optional.<Money> absent();
		else
			return Optional.<Money> of(Money.of(CurrencyUnit.EUR, maxRentPerMonthInEuro));
	}

}
