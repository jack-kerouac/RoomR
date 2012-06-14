package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.application.RoomOfferApplication;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.RoomOffers;
import controllers.rest.RoomrUsers;

public class RoomOfferApplicationSerializer implements JsonSerializer<RoomOfferApplication> {

	@Override
	public JsonElement serialize(RoomOfferApplication application, Type type, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("currentState", application.currentState.toString());
		result.addProperty("message", application.message);
		result.addProperty("applicant", RoomrUsers.getUrlFor(application.applicant));
		result.addProperty("roomOffer", RoomOffers.getUrlFor(application.roomOffer));
		return result;
	}
}
