package models.offer;

import models.common.FloorSpace;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.Interval;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class RoomDetails {

	public final static CurrencyUnit DEFAULT_CURRENCY = CurrencyUnit.EUR;
	
	public double totalRentPerMonthInEuro;
	
	public FloorSpace roomSize;

	public RoomDetails() {}

	public RoomDetails(Interval freePeriod, Money rent) {
		super();
		Preconditions.checkArgument(rent.getCurrencyUnit() == DEFAULT_CURRENCY, "only " + DEFAULT_CURRENCY
				+ " is supported as currency");
		this.totalRentPerMonthInEuro = rent.getAmount().doubleValue();
	}

	public Money getTotalRentPerMonth() {
		return Money.of(DEFAULT_CURRENCY, totalRentPerMonthInEuro);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("rent", getTotalRentPerMonth()).add("room size", roomSize).toString();
	}
	
}
