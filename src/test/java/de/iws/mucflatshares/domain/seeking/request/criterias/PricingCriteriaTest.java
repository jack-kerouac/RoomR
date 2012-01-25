package de.iws.mucflatshares.domain.seeking.request.criterias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;

import de.iws.mucflatshares.domain.offering.offer.Pricing;
import de.iws.mucflatshares.domain.seeking.filter.criterias.PricingCriteria;

public class PricingCriteriaTest {

	private static Money m(int amount) {
		return Money.of(CurrencyUnit.EUR, amount);
	}

	@Test
	public void testApply() {
		PricingCriteria c500 = PricingCriteria.limitedMonthlyTotal(m(500));

		assertTrue(c500.apply(new Pricing(m(500), m(0), m(1000))));
		assertTrue(c500.apply(new Pricing(m(450), m(50), m(1000))));

		assertFalse(c500.apply(new Pricing(m(501), m(0), m(1000))));
		assertFalse(c500.apply(new Pricing(m(450), m(51), m(1000))));
	}

}
