package controllers.rest;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.mvc.Controller;

import com.google.gson.Gson;

import controllers.rest.serialize.FlatshareUrlSerializer;
import controllers.rest.serialize.RoomOfferUrlSerializer;
import controllers.rest.serialize.RoomrUserUrlSerializer;

public class Flatshares extends Controller {
	public static void list() {
		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(Flatshare.class, new FlatshareUrlSerializer())
				.create();
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

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomrUser.class, new RoomrUserUrlSerializer())
				.registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer()).create();
		renderJSON(gson.toJson(flatshare));
	}
}
