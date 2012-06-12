package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.flatshare.Flatshare;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class FlatshareUrlSerializer implements JsonSerializer<Flatshare> {

	@Override
	public JsonElement serialize(Flatshare flatshare, Type typeOfUser, JsonSerializationContext context) {
		String url = Router
				.reverse("rest.Flatshares.get", ImmutableMap.of("id", (Object) String.valueOf(flatshare.id))).url;
		return new JsonPrimitive(url);
	}

}