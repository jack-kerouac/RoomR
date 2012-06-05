package controllers.rest;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RoomOffers extends Controller {
	public static void list() {
		Gson gson = new GsonBuilder().registerTypeAdapter(RoomOffer.class,
				new ModelIdSerializer()).create();
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

		Gson gson = new GsonBuilder()
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
