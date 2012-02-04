package controllers;

import models.Offer;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Offers extends Controller {

	public static void createOffer(@Valid Offer offer) {
		if (validation.hasErrors()) {
			params.flash(); // add http parameters to the flash scope
			validation.keep(); // keep the errors for the next request
			Application.createOffer();
		}
		renderJSON(offer);
	}
}
