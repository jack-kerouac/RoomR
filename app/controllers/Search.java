package controllers;

import java.util.List;

import javax.inject.Inject;

import models.common.Age;
import models.common.Floor;
import models.common.Gender;
import models.ranking.OfferRanker;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

@InjectSupport
public class Search extends AbstractRoomrController {

	@Inject
	private static OfferRanker ranker;

	public static void searchForm(String city) {
		Floor[] floors = Floor.values();
		
		RoomRequest rr = new RoomRequest();
		rr.city = city;
		
		render((Object) floors, rr);
	}

	public static void offers(RoomRequest rr, Age seekerAge, Gender seekerGender) {
		// if no attribute of the request is set, the whole request is null. Thus, create one.
		if(rr == null)
			rr = new RoomRequest();
		
		List<ScoredRoomOffer> offers = ranker.search(rr, Optional.fromNullable(seekerAge),
				Optional.fromNullable(seekerGender));

		render(offers);
	}
	
}
