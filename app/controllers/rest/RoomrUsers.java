package controllers.rest;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.user.RoomrUser;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.Http;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;

import controllers.rest.dto.RegistrationData;
import controllers.rest.serialize.BriefUserSerializer;
import controllers.rest.serialize.FlatExclusionStrategy;
import controllers.rest.serialize.ModelIdSerializer;
import controllers.rest.serialize.NameBasedExclusionStrategy;
import facade.UserFacade;
import facade.exception.UserAlreadyCreatedException;

@InjectSupport
public class RoomrUsers extends Controller {
	@Inject
	private static UserFacade userFacade;

	public static void list() {
		Gson gson = RoomrGsonBuilder
				.builder()
				.registerTypeAdapter(RoomrUser.class, new BriefUserSerializer())
				.create();
		renderJSON(gson.toJson(RoomrUser.all().fetch()));
	}

	public static void create(@Valid RegistrationData registrationData) {
		if (validation.hasErrors()) {
			badRequest();
		}

		RoomrUser user = new RoomrUser();
		user.name = registrationData.name;
		user.email = registrationData.email;
		user.password = registrationData.password;
		user.birthdate = registrationData.birthdate;
		user.gender = registrationData.gender;

		RoomrUser createdUser;
		try {
			createdUser = userFacade.createUser(user);
			get(createdUser.id);
		} catch (UserAlreadyCreatedException e) {
			response.status = Http.StatusCode.BAD_REQUEST;
			renderJSON(ImmutableMap.of("error", "emailAlreadyTaken"));
		}
	}

	private static RoomrUser getRoomrUser(long id) {
		RoomrUser user = RoomrUser.findById(id);
		if (user == null) {
			notFound();
		}
		return user;
	}

	public static void get(long id) {
		RoomrUser user = getRoomrUser(id);

		Gson gson = RoomrGsonBuilder
				.builder()
				.setExclusionStrategies(
						new FlatExclusionStrategy(),
						new NameBasedExclusionStrategy().withExclusionsFor(
								RoomrUser.class, ImmutableSet.of("password")))
				.registerTypeAdapter(Flatshare.class, new ModelIdSerializer())
				.create();
		renderJSON(gson.toJson(user));
	}

	public static void getCurrent() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (loggedInUser.isPresent()) {
			get(loggedInUser.get().id);
		} else {
			unauthorized();
		}
	}

	public static void getApplications(int id) {
		RoomrUser roomrUser = getRoomrUser(id);
		renderJSON(RoomOfferApplications
				.renderApplications(roomrUser.applications));
	}
}
