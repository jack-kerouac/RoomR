package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.offer.RoomOffer;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RoomOfferUrlSerializer implements JsonSerializer<RoomOffer> {

	@Override
	public JsonElement serialize(RoomOffer offer, Type typeOfUser, JsonSerializationContext context) {
		String url = Router.reverse("rest.RoomOffers.get", ImmutableMap.of("id", (Object) String.valueOf(offer.id))).url;
		return new JsonPrimitive(url);
	}

}