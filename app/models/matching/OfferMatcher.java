package models.matching;

import java.util.List;

import models.Offer;

import org.joda.money.Money;

public interface OfferMatcher {

	public List<Offer> match(String quarter, Money money);
	
}
