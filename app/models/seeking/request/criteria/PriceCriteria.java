package models.seeking.request.criteria;

import org.joda.money.Money;

public class PriceCriteria {

	private static class LimitedMonthlyTotal extends PriceCriteria {

		private final Money maxTotalPerMonth;

		public LimitedMonthlyTotal(Money maxTotalPerMonth) {
			this.maxTotalPerMonth = maxTotalPerMonth;
		}
		
		@Override
		public String toString() {
			return "limited price: " + maxTotalPerMonth;
		}

	}

	public static PriceCriteria limitedMonthlyTotal(Money maxTotalPerMonth) {
		return new LimitedMonthlyTotal(maxTotalPerMonth);
	}

	private static class UnlimitedMonthlyTotal extends PriceCriteria {
		@Override
		public String toString() {
			return "unlimited price";
		}
	}

	public static PriceCriteria unlimitedMonthlyTotal() {
		return new UnlimitedMonthlyTotal();
	}
}
