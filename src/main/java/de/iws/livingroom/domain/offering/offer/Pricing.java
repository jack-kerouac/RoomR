package de.iws.livingroom.domain.offering.offer;

import org.joda.money.Money;

public final class Pricing {

	private final Money rentPerMonth;
	private final Money utilitiesPerMonth;
	private final Money deposit;

	public Pricing(Money rentPerMonth, Money utilitiesPerMonth, Money deposit) {
		super();
		this.rentPerMonth = rentPerMonth;
		this.utilitiesPerMonth = utilitiesPerMonth;
		this.deposit = deposit;
	}

	public boolean totalMonthlyPriceIsLessOrEqualThan(Money price) {
		return getTotalCostsPerMonth().isLessThan(price) || getTotalCostsPerMonth().isEqual(price);
	}

	public Money getRentPerMonth() {
		return rentPerMonth;
	}

	public Money getUtilitiesPerMonth() {
		return utilitiesPerMonth;
	}
	
	public Money getTotalCostsPerMonth() {
		return getRentPerMonth().plus(getUtilitiesPerMonth());
	}

	public Money getDeposit() {
		return deposit;
	}

}
