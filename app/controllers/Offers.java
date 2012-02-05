package controllers;

import javax.inject.Inject;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

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
		
		renderJSON(offer);
	}
}
