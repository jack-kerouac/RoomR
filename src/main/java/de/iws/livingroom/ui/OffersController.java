package de.iws.livingroom.ui;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.ModelName;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.views.ToView;

import com.google.inject.Inject;

import de.iws.livingroom.application.OfferFacade;
import de.iws.livingroom.domain.Address;
import de.iws.livingroom.domain.Location;
import de.iws.livingroom.domain.Offer;
import de.iws.livingroom.domain.RoomDetails;

@Controller
public class OffersController {

	@Inject
	private OfferFacade offerManagement;

	// @Path("/(.*)")
	// @ModelName("msg")
	// @ToView("start")
	// public String doAction(@UriParameter(1) String name) {
	// Offer offer1 = offerManagement.create(
	// new Location("Lehel", new Address("Knöbelstraße", 14, 80538, "München")),
	// new RoomDetails(new Interval(DateTime.now(), Duration.standardDays(30)),
	// Money
	// .of(CurrencyUnit.EUR, 300)));
	// Offer offer2 = offerManagement.create(
	// new Location("Lehel", new Address("Knöbelstraße", 12, 80538, "München")),
	// new RoomDetails(new Interval(DateTime.now(), Duration.standardDays(60)),
	// Money
	// .of(CurrencyUnit.EUR, 400)));
	//
	// return "Hello World " + name + "! " + offerManagement.findAll();
	// }

	@Path("/")
	@ModelName("offer")
	@ToView("offer")
//	public Offer doAction(@RequestParameter("fromDate") @DateConv("yyyyMMdd") Date fromDate, @RequestParameter("toDate") @DateConv("yyyyMMdd") Date toDate) {
	public Offer doAction(@RequestParameter("quarter") String quarter) {
		Offer offer = offerManagement.create(
				new Location(quarter, new Address("Knöbelstraße", 14, 80538, "München")),
				new RoomDetails(new Interval(DateTime.now(), Duration.standardDays(30)), Money
						.of(CurrencyUnit.EUR, 300)));
		return offer;
	}

}
