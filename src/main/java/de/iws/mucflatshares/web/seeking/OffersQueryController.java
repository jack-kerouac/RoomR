package de.iws.mucflatshares.web.seeking;

import java.util.Collection;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import de.iws.mucflatshares.application.seeking.SeekerFacade;
import de.iws.mucflatshares.domain.common.FloorSpace;
import de.iws.mucflatshares.domain.common.LivingPeriod;
import de.iws.mucflatshares.domain.market.PublishedRoomOffer;
import de.iws.mucflatshares.domain.market.PublishedRoomOfferRepository;
import de.iws.mucflatshares.domain.offering.flatshare.Quarter;
import de.iws.mucflatshares.domain.seeking.filter.RoomFilter;

@Controller
public class OffersQueryController {

	@Autowired
	private SeekerFacade seekerFacade;

	// @RequestMapping(value = "/home", method = RequestMethod.GET)
	// public ModelAndView home() {
	// ModelAndView modelAndView = new ModelAndView("home");
	// modelAndView.addObject("controllerMessage", "HELLLLO");
	//
	// return modelAndView;
	// }

	@RequestMapping(value = "/market/offers", method = RequestMethod.GET)
	public ModelAndView queryOffersForSeeker(@RequestParam(value = "seekerId", defaultValue = "") String seekerId,
			@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
			@RequestParam(value = "minFloorSpace", required = false) Integer minFloorSpace,
			@RequestParam(value = "quarters", defaultValue = "") String quartersString) {

		Collection<Quarter> quarters = ImmutableSet.copyOf(Iterables.transform(Splitter.on(',').trimResults()
				.omitEmptyStrings().split(quartersString), new Function<String, Quarter>() {
			@Override
			public Quarter apply(String input) {
				return new Quarter(input);
			}
		}));

		RoomFilter.Builder builder = RoomFilter.builder(new LivingPeriod(new DateMidnight(2011, 10, 1),
				new DateMidnight(2011, 11, 30)), "München");
		if (!quarters.isEmpty())
			builder.quarters(quarters);
		if (maxPrice != null)
			builder.maxTotalPricePerMonth(Money.of(CurrencyUnit.EUR, maxPrice));
		if (minFloorSpace != null)
			builder.minRoomFloorSpace(new FloorSpace(minFloorSpace));
		RoomFilter request = builder.build();

		Collection<PublishedRoomOffer> offers;

		if (!seekerId.isEmpty())
			offers = seekerFacade.retrieveOffersForSeeker(seekerId, request);
		else
			offers = seekerFacade.retrieveOffers(request);

		ModelAndView modelAndView = new ModelAndView("offers");

		modelAndView.addObject("seekerId", seekerId);
		modelAndView.addObject("publisherRoomOffers", offers);

		return modelAndView;
	}

	@Autowired
	private PublishedRoomOfferRepository repository;

	@RequestMapping(value = "/market/offers/{offerId}", method = RequestMethod.GET)
	public ModelAndView queryOffersForSeeker(@PathVariable("offerId") int offerId) {
		PublishedRoomOffer offer = repository.findById(offerId);

		ModelAndView modelAndView = new ModelAndView("offerDetails");

		modelAndView.addObject("offer", offer.getOffer());

		return modelAndView;
	}

}
