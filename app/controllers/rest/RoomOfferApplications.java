package controllers.rest;

import java.util.Collection;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;
import play.mvc.Router;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import controllers.rest.dto.RoomOfferApplicationData;
import controllers.rest.serialize.RoomOfferApplicationSerializer;
import controllers.rest.serialize.RoomOfferUrlSerializer;
import controllers.rest.serialize.RoomrUserUrlSerializer;
import facade.SeekerFacade;
import facade.UserFacade;

@InjectSupport
public class RoomOfferApplications extends Controller {

	@Inject
	private static SeekerFacade seekerFacade;

	@Inject
	private static UserFacade userFacade;

	public static String getUrlFor(RoomOfferApplication application) {
		return Router.reverse("rest.RoomOfferApplications.get",
				ImmutableMap.of("id", (Object) String.valueOf(application.id))).url;
	}

	public static String renderApplications(Collection<RoomOfferApplication> applications) {

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer())
				.registerTypeAdapter(RoomrUser.class, new RoomrUserUrlSerializer()).create();

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

		Gson gson = RoomrGsonBuilder.builder().registerTypeAdapter(RoomOffer.class, new RoomOfferUrlSerializer())
				.registerTypeAdapter(RoomrUser.class, new RoomrUserUrlSerializer()).create();
		renderJSON(gson.toJson(roomOfferApplication));
	}

	public static void create(long id, RoomOfferApplicationData applicationData) {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();
		if (loggedInUser.isPresent()) {
			RoomOfferApplication application = seekerFacade.apply(loggedInUser.get(), id, applicationData.message);

			response.setHeader("Location", getUrlFor(application));
			Gson gson = RoomrGsonBuilder.builder()
					.registerTypeAdapter(RoomOfferApplication.class, new RoomOfferApplicationSerializer()).create();
			renderJSON(gson.toJson(application));

		} else {
			unauthorized();
		}
	}
}
