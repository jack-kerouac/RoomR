package controllers.rest;

import models.flatshare.Flatshare;
import models.user.RoomrUser;
import play.mvc.Controller;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RoomrUsers extends Controller {
	public static void list() {
		Gson gson = new GsonBuilder().registerTypeAdapter(RoomrUser.class,
				new BriefUserSerializer()).create();
		renderJSON(gson.toJson(RoomrUser.all().fetch()));
	}

	private static RoomrUser getRoomrUser(int id) {
		RoomrUser user = RoomrUser.findById(Long.valueOf(id));
		if (user == null) {
			notFound();
		}
		return user;
	}

	public static void get(int id) {
		RoomrUser user = getRoomrUser(id);

		Gson gson = new GsonBuilder()
				.setExclusionStrategies(
						new FlatExclusionStrategy(),
						new NameBasedExclusionStrategy().withExclusionsFor(
								RoomrUser.class, ImmutableSet.of("password")))
				.registerTypeAdapter(Flatshare.class, new ModelIdSerializer())
				.create();
		renderJSON(gson.toJson(user));
	}

	public static void getApplications(int id) {
		RoomrUser roomrUser = getRoomrUser(id);
		renderJSON(RoomOfferApplications
				.renderApplications(roomrUser.applications));
	}
}
