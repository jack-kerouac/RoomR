package controllers;

import java.util.List;

import javax.inject.Inject;

import models.common.Floor;
import models.matching.OfferMatcher;
import models.offer.Offer;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import play.modules.guice.InjectSupport;
import play.mvc.Controller;

@InjectSupport
public class Search extends Controller {

	@Inject
	private static OfferMatcher matcher;

	public static void searchForm() {
		Floor[] floors = Floor.values();
		render((Object)floors);
	}

	public static void offers(String maxRent, Floor floor) {
		if(floor == null && (maxRent == null || maxRent.isEmpty()))
			render();
		
		Money maxRentMoney = maxRent.isEmpty() ? Money.of(CurrencyUnit.EUR, 100000.0) : Money.of(CurrencyUnit.EUR, Double.valueOf(maxRent));
		
		List<Offer> offers = matcher.match(maxRentMoney, floor);
		
		render(offers);
	}
}
