package controllers;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import models.common.Age;
import models.common.FloorSpace;
import models.common.Gender;
import models.ranking.matching.ScoredRoomOffer;
import models.request.RoomRequest;
import models.request.RoomRequest.DateQuery;
import models.user.RoomrUser;
import play.cache.Cache;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import controllers.formdata.InstantSearchFormData;
import controllers.formdata.InstantSearchFormData.StartDateType;
import facade.SeekerFacade;
import facade.UserFacade;

@InjectSupport
public class Search extends AbstractRoomrController {
	public static final String INSTANT_SEARCH_DATA_CACHE_KEY = "-instantSearchData";

	@Inject
	private static UserFacade userFacade;

	@Inject
	private static SeekerFacade seekerFacade;

	public static void searchForm(String city, Double max_rent, Date startDate, Integer age, Gender gender) {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();

		InstantSearchFormData formData = new InstantSearchFormData();

		// AGE
		if (age == null && loggedInUser.isPresent()) {
			formData.age = loggedInUser.get().getAge();
		}
		else {
			formData.age = age;
		}
		// GENDER
		if (gender == null && loggedInUser.isPresent()) {
			gender = loggedInUser.get().gender;
		}
		else {
			formData.gender = gender;
		}

		// START DATE
		if (startDate == null) {
			formData.startDateType = StartDateType.now;
		}
		else {
			formData.startDateType = StartDateType.fixedDate;
			formData.startDate = startDate;
		}

		formData.city = city;
		formData.maxRentPerMonthInEuro = max_rent;

		render(formData);
	}

	public static void offers(InstantSearchFormData formData) {
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

		// START DATE
		if (formData.startDateType != null) {
			switch (formData.startDateType) {
			case now:
				rr.startDateQuery = DateQuery.now();
				break;
			case fixedDate:
				validation.required("formData.maxAge", formData.startDate);
				if (formData.startDate != null)
					rr.startDateQuery = DateQuery.fixedDate(formData.startDate);
			}
		}
		else {
			rr.startDateQuery = null;
		}

		rr.city = formData.city;
		rr.maxRentPerMonthInEuro = formData.maxRentPerMonthInEuro;
		if (formData.minRoomSizeSquareMeters != null)
			rr.minRoomSize = new FloorSpace(formData.minRoomSizeSquareMeters);

		List<ScoredRoomOffer> offers = seekerFacade.search(rr, Optional.fromNullable(seekerAge),
				Optional.fromNullable(formData.gender));

		Cache.set(session.getId() + INSTANT_SEARCH_DATA_CACHE_KEY, formData, "30min");

		render(offers);
	}
}