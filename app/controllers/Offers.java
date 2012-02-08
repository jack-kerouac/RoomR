package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.Address;
import models.Location;
import models.Offer;
import models.OfferRepository;
import models.RoomDetails;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

@InjectSupport
public class Offers extends Controller {

	@Inject
	private static OfferRepository offerRepository;

	public static void offerForm(Offer offer) {
		if (offer == null)
			// no offer to prefill form withs
			offer = new Offer(new Location("Lehel", new Address("", 0, 0, "")), new RoomDetails(new Interval(
					new DateTime(), Duration.standardDays(365)), Money.of(CurrencyUnit.EUR, 0)));
		render(offer);
	}


	public static void createOffer(@Valid Offer offer) {
		if (validation.hasErrors()) {
//			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(offer);
		}

		offerRepository.add(offer);

		viewOffer(offer.getId());
	}

	public static void viewAll() {
		Set<Offer> offers = offerRepository.findAll();
		render(offers);
	}

	public static void viewOffer(long id) {
		Offer offer = offerRepository.find(id);
		render(offer);
	}

}
