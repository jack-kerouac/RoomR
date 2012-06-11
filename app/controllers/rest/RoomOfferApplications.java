package controllers.rest;

import java.util.Collection;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import controllers.rest.serialize.ModelIdSerializer;
import facade.SeekerFacade;
import facade.UserFacade;

@InjectSupport
public class RoomOfferApplications extends Controller {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static UserFacade userFacade;

	public static String renderApplications(Collection<RoomOfferApplication> applications) {

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
				.registerTypeAdapter(RoomrUser.class, new ModelIdSerializer()).create();

		return gson.toJson(applications);
	}

	private static RoomOfferApplication getRoomOfferApplication(int id) {
		RoomOfferApplication roomOfferApplication = RoomOfferApplication.findById(Long.valueOf(id));
		if (roomOfferApplication == null) {
			notFound();
		}
		return roomOfferApplication;
	}

	public static void get(int id) {
		RoomOfferApplication roomOfferApplication = getRoomOfferApplication(id);

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new ModelIdSerializer())
				.registerTypeAdapter(RoomrUser.class, new ModelIdSerializer()).create();
		renderJSON(gson.toJson(roomOfferApplication));
	}

	public static void create(long roomOfferId, String message) {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (loggedInUser.isPresent()) {
			RoomOfferApplication application = seekerFacade.apply(loggedInUser.get(), roomOfferId, message);
			renderJSON(ImmutableMap.of("application", application));
		} else {
			unauthorized();
		}
	}

}
