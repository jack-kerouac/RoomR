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

	public static void searchForm() {
		Floor[] floors = Floor.values();
		render((Object) floors);
	}

	public static void offers(RoomRequest request, Age seekerAge, Gender seekerGender) {
		// if no attribute of the request is set, the whole request is null. Thus, create one.
		if(request == null)
			request = new RoomRequest();
		
		List<ScoredRoomOffer> offers = ranker.search(request, Optional.fromNullable(seekerAge),
				Optional.fromNullable(seekerGender));

		render(offers);
	}
	
}
