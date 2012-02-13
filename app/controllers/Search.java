package controllers;

import java.util.List;

import javax.inject.Inject;

import models.common.Age;
import models.common.Floor;
import models.common.Gender;
import models.matching.OfferMatcher;
import models.matching.RankedRoomOffer;
import models.request.RoomRequest;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

import com.google.common.base.Optional;

@InjectSupport
public class Search extends Controller {

	@Inject
	private static OfferMatcher matcher;

	public static void searchForm() {
		Floor[] floors = Floor.values();
		render((Object) floors);
	}

	public static void offers(RoomRequest request, Age seekerAge, Gender seekerGender) {
		List<RankedRoomOffer> offers = matcher.match(request, Optional.fromNullable(seekerAge),
				Optional.fromNullable(seekerGender));

		render(offers);
	}
	
}
