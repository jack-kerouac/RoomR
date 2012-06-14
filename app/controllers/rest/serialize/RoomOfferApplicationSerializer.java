package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.application.RoomOfferApplication;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RoomOfferApplicationSerializer implements JsonSerializer<RoomOfferApplication> {

	@Override
	public JsonElement serialize(RoomOfferApplication application, Type typeOfUser, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("currentState", application.currentState.toString());
		result.addProperty("message", application.message);
		String applicantUrl = Router.reverse("rest.RoomrUsers.get",
				ImmutableMap.of("id", (Object) String.valueOf(application.applicant.id))).url;
		result.addProperty("applicant", applicantUrl);
		String roomOfferUrl = Router.reverse("rest.RoomOffers.get",
				ImmutableMap.of("id", (Object) String.valueOf(application.roomOffer.id))).url;
		result.addProperty("currentState", roomOfferUrl);
		return result;
	}
}
