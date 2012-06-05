package controllers.rest;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Flatshares extends Controller {
	public static void list() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Flatshare.class,
				new ModelIdSerializer()).create();
		renderJSON(gson.toJson(Flatshare.all().fetch()));
	}

	private static Flatshare getFlatshare(int id) {
		Flatshare flatshare = Flatshare.findById(Long.valueOf(id));
		if (flatshare == null) {
			notFound();
		}
		return flatshare;
	}

	public static void get(int id) {
		Flatshare flatshare = getFlatshare(id);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(RoomrUser.class, new ModelIdSerializer())
				.registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
				.create();
		renderJSON(gson.toJson(flatshare));
	}
}
