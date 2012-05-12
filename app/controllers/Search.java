package controllers;

import java.util.List;

import javax.inject.Inject;

import models.common.Age;
import models.common.FloorSpace;
import models.common.Gender;
import models.ranking.OfferRanker;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.request.RoomRequest.DateQuery;
import models.user.RoomrUser;
import play.libs.Json;
import play.mvc.Result;

import com.google.common.base.Optional;

import controllers.formdata.InstantSearchFormData;
import facade.UserFacade;

public class Search extends AbstractRoomrController {
	public static final String INSTANT_SEARCH_DATA_CACHE_KEY = "-instantSearchData";

	@Inject
	private static OfferRanker ranker;

	@Inject
	private static UserFacade userFacade;

	public static Result searchForm(String city, Double max_rent, Integer age,
			Gender gender) {

		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();

		InstantSearchFormData formData = new InstantSearchFormData();

		if (age == null && loggedInUser.isPresent()) {
			formData.age = loggedInUser.get().getAge();
		} else {
			formData.age = age;
		}
		if (gender == null && loggedInUser.isPresent()) {
			gender = loggedInUser.get().gender;
		} else {
			formData.gender = gender;
		}

		formData.city = city;
		formData.maxRentPerMonthInEuro = max_rent;

		// render(formData);
		return ok("");
	}

	public static Result offers(InstantSearchFormData formData) {
		// if no attribute of the request is set, the whole request is null.
		// Thus, create one.
		if (formData == null) {
			formData = new InstantSearchFormData();
		}

		Age seekerAge = null;
		if (formData.age != null) {
			seekerAge = new Age(formData.age);
		}

		RoomRequest rr = new RoomRequest();

		// TODO: change this: null should mean, date not set, not "now"
		if (formData.startDate == null)
			rr.startDateQuery = DateQuery.now();
		else
			rr.startDateQuery = DateQuery.fixedDate(formData.startDate);

		rr.city = formData.city;
		rr.maxRentPerMonthInEuro = formData.maxRentPerMonthInEuro;
		if (formData.minRoomSizeSquareMeters != null)
			rr.minRoomSize = new FloorSpace(formData.minRoomSizeSquareMeters);

		List<ScoredRoomOffer> offers = ranker.search(rr,
				Optional.fromNullable(seekerAge),
				Optional.fromNullable(formData.gender));

		// Cache.set(session.getId() + INSTANT_SEARCH_DATA_CACHE_KEY, formData,
		// "30min");

		return ok(Json.toJson(offers));
	}
}