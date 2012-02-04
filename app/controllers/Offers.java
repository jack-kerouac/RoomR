package controllers;

import models.Offer;
import play.mvc.Controller;

public class Offers extends Controller {
	
	public static void createOffer(Offer offer) {
		renderJSON(offer);
	}
}
