package models.matching.trivial;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import models.matching.OfferMatcher;
import models.offer.Offer;
import models.offer.OfferRepository;

import org.joda.money.Money;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TrivialMatcher implements OfferMatcher {

	@Inject
	private OfferRepository offerRepository;

	@Override
	public List<Offer> match(final String quarterPrefix, final Money maxRent) {
		Set<Offer> allOffers = offerRepository.findAll();

		Set<Offer> offers = Sets.filter(allOffers, new Predicate<Offer>() {
			@Override
			public boolean apply(Offer offer) {
				Money rent = offer.getRoomDetails().getRent();
				return offer.getLocation().getQuarter().startsWith(quarterPrefix)
						&& (rent.isLessThan(maxRent) || rent.isEqual(maxRent));
			}
		});

		return Lists.newArrayList(offers);
	}

}
