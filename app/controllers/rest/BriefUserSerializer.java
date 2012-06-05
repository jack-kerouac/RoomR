package controllers.rest;

import java.lang.reflect.Type;

import models.user.RoomrUser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BriefUserSerializer implements JsonSerializer<RoomrUser> {

	@Override
	public JsonElement serialize(RoomrUser user, Type typeOfUser,
			JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("id", user.id);
		result.addProperty("name", user.name);
		return result;
	}
}