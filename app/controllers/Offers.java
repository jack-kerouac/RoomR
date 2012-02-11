package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.common.Address;
import models.offer.Location;
import models.offer.Offer;
import models.offer.OfferRepository;
import models.offer.RoomDetails;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

import com.google.common.collect.Lists;

@InjectSupport
public class Offers extends Controller {

	@Inject
	private static OfferRepository offerRepository;

	public static void offerForm(Offer offer) {
		if (offer == null)
			// no offer to prefill form withs
			offer = createEmptyOffer();
		render(offer);
	}

	private static Offer createEmptyOffer() {
		return new Offer(new Location("Lehel", new Address("", 0, 0, "")), new RoomDetails(new Interval(new DateTime(),
				Duration.standardDays(365)), Money.of(CurrencyUnit.EUR, 0)));
	}

	public static void createOffer(@Valid Offer offer) {
		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(offer);
		}

		offerRepository.add(offer);

		viewOffer(offer.getId());
	}

	public static void viewAll() {
		ArrayList<Offer> offers = Lists.newArrayList(offerRepository.findAll());
		render(offers);
	}

	public static void viewOffer(long id) {
		Offer offer = offerRepository.find(id);
		render(offer);
	}

}
