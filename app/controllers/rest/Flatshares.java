package controllers.rest;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;

import controllers.rest.serialize.FlatshareUrlSerializer;
import controllers.rest.serialize.RoomOfferUrlSerializer;
import controllers.rest.serialize.RoomrUserUrlSerializer;
import facade.ResidentFacade;

@InjectSupport
public class Flatshares extends Controller {
	@Inject
	private static ResidentFacade residentFacade;

	public static String getUrlFor(Flatshare flatshare) {
		return Router.reverse("rest.Flatshares.get", ImmutableMap.of("id", (Object) String.valueOf(flatshare.id))).url;
	}

	public static void list() {
		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(Flatshare.class, new FlatshareUrlSerializer())
				.create();
		renderJSON(gson.toJson(Flatshare.all().fetch()));
	}

	private static Flatshare getFlatshare(long id) {
		Flatshare flatshare = Flatshare.findById(id);
		if (flatshare == null) {
			notFound();
		}
		return flatshare;
	}

	public static void create(@Valid Flatshare flatshare) {
		Set<RoomOffer> roomOffers = flatshare.roomOffers;
		if (roomOffers.size() > 1) {
			response.print("At most one room offer is allowed to be created together with the flatshare!");
			badRequest();
		}

		flatshare.roomOffers = Collections.emptySet();

		Flatshare createdFlatshare;
		if (roomOffers.isEmpty()) {
			createdFlatshare = residentFacade.createFlatshare(flatshare);
		} else {
			createdFlatshare = residentFacade.createFlatshareAndOffer(flatshare, Iterables.getOnlyElement(roomOffers));
		}

		response.status = Http.StatusCode.CREATED;
		response.setHeader("Location", getUrlFor(createdFlatshare));
		renderJSON(createFlatshareGson().toJson(createdFlatshare));
	}

	public static void update(long id, @Valid Flatshare flatshare) {
		flatshare.id = id;
		residentFacade.updateFlatshare(flatshare);

		response.status = Http.StatusCode.NO_RESPONSE;
	}

	public static void get(long id) {
		Flatshare flatshare = getFlatshare(id);

		renderJSON(createFlatshareGson().toJson(flatshare));
	}

	public static void getRoomOffers(long id) {
		Flatshare flatshare = getFlatshare(id);

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer())
				.create();
		renderJSON(gson.toJson(flatshare.roomOffers));
	}

	private static Gson createFlatshareGson() {
		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomrUser.class, new RoomrUserUrlSerializer())
				.registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer()).create();
		return gson;
	}
}
