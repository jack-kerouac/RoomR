package controllers;

import java.util.List;

import javax.inject.Inject;

import models.common.Age;
import models.common.Floor;
import models.common.FloorSpace;
import models.common.Gender;
import models.ranking.OfferRanker;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import play.cache.Cache;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import controllers.formdata.InstantSearchFormData;

@InjectSupport
public class Search extends AbstractRoomrController {
	public static final String INSTANT_SEARCH_DATA_CACHE_KEY = "-instantSearchData";

	@Inject
	private static OfferRanker ranker;

	public static void searchForm(String city, double max_rent, int age, Gender gender) {
		Floor[] floors = Floor.values();

		InstantSearchFormData formData = new InstantSearchFormData();
		formData.city = city;
		formData.age = age;
		formData.maxRentPerMonthInEuro = max_rent;
		formData.gender = gender;

		render((Object) floors, formData);
	}

	public static void offers(InstantSearchFormData formData) {
		// if no attribute of the request is set, the whole request is null.
		// Thus, create one.
		if (formData == null)
			formData = new InstantSearchFormData();

		Age seekerAge = null;
		if (formData.age != null) {
			seekerAge = new Age(formData.age);
		}

		RoomRequest rr = new RoomRequest();
		rr.city = formData.city;
		rr.maxRentPerMonthInEuro = formData.maxRentPerMonthInEuro;
		rr.minRoomSize = new FloorSpace(formData.minRoomSizeSquareMeters);

		List<ScoredRoomOffer> offers = ranker.search(rr, Optional.fromNullable(seekerAge),
				Optional.fromNullable(formData.gender));

		Cache.set(session.getId() + INSTANT_SEARCH_DATA_CACHE_KEY, formData, "30min");

		render(offers);
	}

}