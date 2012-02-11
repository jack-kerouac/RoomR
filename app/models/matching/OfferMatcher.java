package models.matching;

import java.util.List;

import models.offer.Offer;

import org.joda.money.Money;

public interface OfferMatcher {

	public List<Offer> match(String quarter, Money money);
	
}
