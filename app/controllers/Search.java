package controllers;

import java.util.List;

import javax.inject.Inject;

import models.Offer;
import models.matching.OfferMatcher;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import play.modules.guice.InjectSupport;
import play.mvc.Controller;

@InjectSupport
public class Search extends Controller {

	@Inject
	private static OfferMatcher matcher;

	public static void searchForm() {
		render();
	}

	public static void offers(String quarter, String maxRent) {
		if(quarter.isEmpty() && maxRent.isEmpty())
			render();
		
		Money maxRentMoney = maxRent.isEmpty() ? Money.of(CurrencyUnit.EUR, 100000.0) : Money.of(CurrencyUnit.EUR, Double.valueOf(maxRent));
		
		List<Offer> offers = matcher.match(quarter, maxRentMoney);
		
		render(offers);
	}
}
