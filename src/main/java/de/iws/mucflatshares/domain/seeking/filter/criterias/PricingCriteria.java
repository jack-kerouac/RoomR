package de.iws.mucflatshares.domain.seeking.filter.criterias;

import org.joda.money.Money;

import com.google.common.base.Predicate;

import de.iws.mucflatshares.domain.offering.offer.Pricing;

public abstract class PricingCriteria implements Predicate<Pricing> {

	public final static PricingCriteria limitedMonthlyTotal(Money maxTotalPerMonth) {
		return new LimitedMonthlyTotal(maxTotalPerMonth);
	}

	private final static class LimitedMonthlyTotal extends PricingCriteria implements Predicate<Pricing> {

		private final Money maxTotalPerMonth;

		public LimitedMonthlyTotal(Money maxTotalPerMonth) {
			super();
			this.maxTotalPerMonth = maxTotalPerMonth;
		}

		@Override
		public boolean apply(Pricing pricing) {
			return pricing.totalMonthlyPriceIsLessOrEqualThan(maxTotalPerMonth);
		}

	}

	public final static PricingCriteria unlimitedMonthlyTotal() {
		return new UnlimitedMonthlyTotal();
	}

	private final static class UnlimitedMonthlyTotal extends PricingCriteria implements Predicate<Pricing> {

		@Override
		public boolean apply(Pricing pricing) {
			return true;
		}

	}

}