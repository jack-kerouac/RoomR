package controllers.rest;

import java.util.Collection;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RoomOfferApplications extends Controller {
	public static String renderApplications(
			Collection<RoomOfferApplication> applications) {

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
				.registerTypeAdapter(RoomrUser.class, new ModelIdSerializer())
				.create();

		return gson.toJson(applications);
	}

	private static RoomOfferApplication getRoomOfferApplication(int id) {
		RoomOfferApplication roomOfferApplication = RoomOfferApplication
				.findById(Long.valueOf(id));
		if (roomOfferApplication == null) {
			notFound();
		}
		return roomOfferApplication;
	}

	public static void get(int id) {
		RoomOfferApplication roomOfferApplication = getRoomOfferApplication(id);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
				.registerTypeAdapter(RoomrUser.class, new ModelIdSerializer())
				.create();
		renderJSON(gson.toJson(roomOfferApplication));
	}

}
