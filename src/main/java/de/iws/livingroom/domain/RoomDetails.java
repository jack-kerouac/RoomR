package de.iws.livingroom.domain;

import java.util.Date;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.appengine.api.search.Preconditions;
import com.google.appengine.repackaged.com.google.common.base.Objects;

public final class RoomDetails {

	public final static CurrencyUnit DEFAULT_CURRENCY = CurrencyUnit.EUR;

	private Date freePeriodStart;
	private Date freePeriodEnd;

	private double rentAmount;

	@SuppressWarnings("unused")
	private RoomDetails() {}

	public RoomDetails(Interval freePeriod, Money rent) {
		super();
		freePeriodStart = freePeriod.getStart().toDate();
		freePeriodEnd = freePeriod.getEnd().toDate();

		Preconditions.checkArgument(rent.getCurrencyUnit() == DEFAULT_CURRENCY, "only " + DEFAULT_CURRENCY
				+ " is supported as currency");
		this.rentAmount = rent.getAmount().doubleValue();
	}

	public Interval getFreePeriod() {
		return new Interval(new DateTime(freePeriodStart), new DateTime(freePeriodEnd));
	}

	public Money getRent() {
		return Money.of(DEFAULT_CURRENCY, rentAmount);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("free period", getFreePeriod()).add("rent", getRent()).toString();
	}
	
}
