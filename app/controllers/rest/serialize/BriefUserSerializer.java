package controllers.rest.serialize;

import java.lang.reflect.Type;

import controllers.rest.RoomrUsers;
import models.user.RoomrUser;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BriefUserSerializer implements JsonSerializer<RoomrUser> {

	@Override
	public JsonElement serialize(RoomrUser user, Type type, JsonSerializationContext context) {
		String url = RoomrUsers.getUrlFor(user);
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("name", user.name);
		return result;
	}
}