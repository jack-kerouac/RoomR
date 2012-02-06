package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.Offer;
import models.OfferRepository;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;


@InjectSupport
public class Offers extends Controller {

	@Inject
	private static OfferRepository offerRepository;
	
	
	public static void createOffer(@Valid Offer offer) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Application.createOffer();
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
