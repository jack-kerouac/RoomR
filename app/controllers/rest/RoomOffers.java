package controllers.rest;

import java.util.List;

import javax.inject.Inject;

import models.common.FloorSpace;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.request.RoomRequest.DateQuery;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.Router;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import controllers.rest.dto.SearchData;
import controllers.rest.serialize.FlatExclusionStrategy;
import controllers.rest.serialize.FlatshareUrlSerializer;
import controllers.rest.serialize.RoomOfferUrlSerializer;
import facade.SeekerFacade;

@InjectSupport
public class RoomOffers extends Controller {

	@Inject
	private static SeekerFacade seekerFacade;

	public static String getUrlFor(RoomOffer offer) {
		return Router.reverse("rest.RoomOffers.get", ImmutableMap.of("id", (Object) String.valueOf(offer.id))).url;
	}

	public static void list() {
		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer())
				.create();
		renderJSON(gson.toJson(RoomOffer.all().fetch()));
	}

	private static RoomOffer getRoomOffer(int id) {
		RoomOffer roomOffer = RoomOffer.findById(Long.valueOf(id));
		if (roomOffer == null) {
			notFound();
		}
		return roomOffer;
	}

	public static void get(int id) {
		RoomOffer offer = getRoomOffer(id);

		Gson gson = RoomrGsonBuilder.builder().setExclusionStrategies(new FlatExclusionStrategy())
				.registerTypeAdapter(Flatshare.class, new FlatshareUrlSerializer()).create();
		renderJSON(gson.toJson(offer));
	}

	public static void getApplications(int id) {
		RoomOffer offer = getRoomOffer(id);
		renderJSON(RoomOfferApplications.renderApplications(offer.applications));
	}

	public static void search(SearchData searchData) {
		// if no attribute of the request is set, the whole request is null.
		// Thus, create one.
		if (searchData == null) {
			searchData = new SearchData();
		}

		Integer seekerAge = null;
		if (searchData.age != null) {
			seekerAge = searchData.age;
		}

		RoomRequest rr = new RoomRequest();

		// START DATE
		if (searchData.startDateType != null) {
			switch (searchData.startDateType) {
			case now:
				rr.startDateQuery = DateQuery.now();
				break;
			case fixedDate:
				if (searchData.startDate != null)
					rr.startDateQuery = DateQuery.fixedDate(searchData.startDate);
			}
		} else {
			rr.startDateQuery = null;
		}

		rr.city = searchData.city;
		rr.maxRentPerMonthInEuro = searchData.maxRentPerMonthInEuro;
		if (searchData.minRoomSizeSquareMeters != null)
			rr.minRoomSize = new FloorSpace(searchData.minRoomSizeSquareMeters);

		List<ScoredRoomOffer> offers = seekerFacade.search(rr, Optional.fromNullable(seekerAge),
				Optional.fromNullable(searchData.gender));

		Gson gson = RoomrGsonBuilder.builder().setExclusionStrategies(new FlatExclusionStrategy())
				.registerTypeAdapter(Flatshare.class, new FlatshareUrlSerializer()).create();

		renderJSON(gson.toJson(offers));
	}
}
