package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.common.Age;
import models.common.Floor;
import models.common.Gender;
import models.flatshare.SmokingTolerance;
import models.offer.Offer;
import models.offer.OfferRepository;
import models.offer.SeekerCriteria;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@InjectSupport
public class Offers extends Controller {

	@Inject
	private static OfferRepository offerRepository;

	public static void offerForm(Offer offer) {
		if (offer == null) {
			// no offer to prefill form with
			offer = new Offer();
			offer.criteria = new SeekerCriteria();
			offer.criteria.minAge = new Age(18);
			offer.criteria.maxAge = new Age(50);
			offer.criteria.genders = Sets.newHashSet(Gender.male, Gender.female);
		}
		
		Floor[] floors = Floor.values();
		SmokingTolerance[] smokingTolerances = SmokingTolerance.values();
		render(offer, floors, smokingTolerances);
	}

	public static void createOffer(@Valid Offer offer) {
		if (validation.hasErrors()) {
			// params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Offers.offerForm(offer);
		}

		offerRepository.add(offer);

		viewOffer(offer.id);
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
