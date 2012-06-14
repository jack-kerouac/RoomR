package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.offer.RoomOffer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.RoomOffers;

public class RoomOfferUrlSerializer implements JsonSerializer<RoomOffer> {

	@Override
	public JsonElement serialize(RoomOffer offer, Type type, JsonSerializationContext context) {
		String url = RoomOffers.getUrlFor(offer);
		return new JsonPrimitive(url);
	}

}