package models.offer;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import models.common.FloorSpace;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

@Embeddable
public final class RoomDetails {

	public final static CurrencyUnit DEFAULT_CURRENCY = CurrencyUnit.EUR;

	public double totalRentPerMonthInEuro;
	public double depositInEuro;

	public Date freeFrom;
	public Date freeTo;

	@Embedded
	public FloorSpace roomSize;

	public RoomDetails() {
	}

	public RoomDetails(Money rent) {
		Preconditions.checkArgument(rent.getCurrencyUnit() == DEFAULT_CURRENCY,
				"only " + DEFAULT_CURRENCY + " is supported as currency");
		this.totalRentPerMonthInEuro = rent.getAmount().doubleValue();
	}

	public Money getTotalRentPerMonth() {
		return Money.of(DEFAULT_CURRENCY, totalRentPerMonthInEuro);
	}

	public Money getDeposit() {
		return Money.of(DEFAULT_CURRENCY, depositInEuro);
	}

	@Override
	public String toString() {
		return Objects
				.toStringHelper(this)
				.add("rent", getTotalRentPerMonth())
				.add("room size", roomSize)
				.add("rent period",
						freeFrom + " - "
								+ (freeTo == null ? "unlimited" : freeTo))
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((freeFrom == null) ? 0 : freeFrom.hashCode());
		result = prime * result + ((freeTo == null) ? 0 : freeTo.hashCode());
		result = prime * result
				+ ((roomSize == null) ? 0 : roomSize.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalRentPerMonthInEuro);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomDetails other = (RoomDetails) obj;
		if (freeFrom == null) {
			if (other.freeFrom != null)
				return false;
		} else if (!freeFrom.equals(other.freeFrom))
			return false;
		if (freeTo == null) {
			if (other.freeTo != null)
				return false;
		} else if (!freeTo.equals(other.freeTo))
			return false;
		if (roomSize == null) {
			if (other.roomSize != null)
				return false;
		} else if (!roomSize.equals(other.roomSize))
			return false;
		if (Double.doubleToLongBits(totalRentPerMonthInEuro) != Double
				.doubleToLongBits(other.totalRentPerMonthInEuro))
			return false;
		return true;
	}

}
