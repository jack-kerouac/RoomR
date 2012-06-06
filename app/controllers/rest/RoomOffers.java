package controllers.rest;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import play.mvc.Controller;

import com.google.gson.Gson;

import controllers.rest.serialize.FlatExclusionStrategy;
import controllers.rest.serialize.ModelIdSerializer;

public class RoomOffers extends Controller {
	public static void list() {
		Gson gson = RoomrGsonBuilder.builder()
				.registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
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

		Gson gson = RoomrGsonBuilder.builder()
				.setExclusionStrategies(new FlatExclusionStrategy())
				.registerTypeAdapter(Flatshare.class, new ModelIdSerializer())
				.create();
		renderJSON(gson.toJson(offer));
	}

	public static void getApplications(int id) {
		RoomOffer offer = getRoomOffer(id);
		renderJSON(RoomOfferApplications.renderApplications(offer.applications));
	}
}
