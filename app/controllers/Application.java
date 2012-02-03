package controllers;

import models.Address;
import models.Location;
import models.Offer;
import models.RoomDetails;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import play.mvc.Controller;


public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void createOffer() {
		Offer offer = new Offer(new Location("Lehel", new Address("", 0, 0, "")),
				new RoomDetails(new Interval(new DateTime(),
						Duration.standardDays(365)), Money.of(CurrencyUnit.EUR,
						0)));
		render(offer);
	}
	
	public static void createRequest() {
		render();
	}

}