package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.flatshare.Flatshare;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.Flatshares;

public class FlatshareUrlSerializer implements JsonSerializer<Flatshare> {

	@Override
	public JsonElement serialize(Flatshare flatshare, Type typeOfUser, JsonSerializationContext context) {
		String url = Flatshares.getUrlFor(flatshare);
		return new JsonPrimitive(url);
	}

}