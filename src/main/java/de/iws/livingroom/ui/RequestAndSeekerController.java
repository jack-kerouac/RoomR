package de.iws.livingroom.ui;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.joda.money.Money;
import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.ModelName;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.convertors.DateConv;
import org.zdevra.guice.mvc.views.ToView;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableSet;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import de.iws.livingroom.application.RequestFacade;
import de.iws.livingroom.domain.common.FloorSpace;
import de.iws.livingroom.domain.offering.flatshare.Quarter;
import de.iws.livingroom.domain.seeking.request.FlatshareRequest;
import de.iws.livingroom.domain.seeking.request.criteria.FlatmatesCriteria;
import de.iws.livingroom.domain.seeking.request.criteria.LivingPeriodCriteria;
import de.iws.livingroom.domain.seeking.request.criteria.LocationCriteria;
import de.iws.livingroom.domain.seeking.request.criteria.PriceCriteria;
import de.iws.livingroom.domain.seeking.request.criteria.RoomCriteria;

@Controller
public class RequestAndSeekerController {

	@Inject
	private RequestFacade requestFacade;

	@Path("/")
	@ModelName("request")
	@ToView("offers")
	// @formatter:off
	public FlatshareRequest doAction(
			// LIVING PERIOD
			@RequestParameter("livingPeriod") String livingPeriodChoice,
			@RequestParameter("startDate") @DateConv(value = "MM/dd/yyyy", defaultValue = "01/01/2099") Date startDate,
			@RequestParameter("fromDate") @DateConv(value = "MM/dd/yyyy", defaultValue = "01/01/2099") Date fromDate,
			@RequestParameter("toDate") @DateConv(value = "MM/dd/yyyy", defaultValue = "01/01/2099") Date toDate,

			// PRICE CRITERIA
			@RequestParameter("maxrent") String maxRent,

			// ROOM CRITERIA
			@RequestParameter("minroomsize") String minRoomSize,

			// LOCATION CRITERIA
			//@RequestParameter("quarter") String[] quarters
			HttpServletRequest request
			) {
		// @formatter:on

		// LIVING PERIOD
		LivingPeriodCriteria livingPeriodCriteria;
		if (livingPeriodChoice.equals("fromNowOn"))
			livingPeriodCriteria = LivingPeriodCriteria.fromNowOn();
		else if (livingPeriodChoice.equals("startDate"))
			livingPeriodCriteria = LivingPeriodCriteria.startDate(new DateMidnight(startDate));
		else if (livingPeriodChoice.equals("limitedPeriod"))
			livingPeriodCriteria = LivingPeriodCriteria.limitedPeriod(new Interval(new DateMidnight(fromDate),
					new DateMidnight(toDate)));
		else
			throw new IllegalArgumentException("living period type " + livingPeriodChoice + " not known");

		// PRICE CRITERIA
		PriceCriteria priceCriteria;
		if (maxRent.isEmpty())
			priceCriteria = PriceCriteria.unlimitedMonthlyTotal();
		else
			priceCriteria = PriceCriteria.limitedMonthlyTotal(Money.parse("EUR " + maxRent.replaceAll(",", ".")));

		// ROOM CRITERIA
		RoomCriteria roomCriteria;
		if (minRoomSize.isEmpty())
			roomCriteria = RoomCriteria.arbitraryFloorSpace();
		else
			roomCriteria = RoomCriteria.minFloorSpace(new FloorSpace(Double.valueOf(minRoomSize)));

		// LOCATION CRITERIA
		String[] quarters = request.getParameterValues("quarter");
		
		LocationCriteria locationCriteria;
		if (quarters.length == 0)
			locationCriteria = LocationCriteria.everywhere();
		else
			locationCriteria = LocationCriteria.inQuarters(ImmutableSet.<Quarter>copyOf(Iterables.transform(
					ImmutableSet.copyOf(quarters), new Function<String, Quarter>() {
						@Override
						public Quarter apply(String from) {
							return new Quarter(from);
						}
					})));
		
		// FLATMATE CRITERIA
		// TODO: continue here
		FlatmatesCriteria flatmatesCriteria = null;
		

		System.out.println(livingPeriodCriteria);
		System.out.println(priceCriteria);
		System.out.println(roomCriteria);
		System.out.println(locationCriteria);
		System.out.println(flatmatesCriteria);

		return null;
	}
}
