package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.user.RoomrUser;
import play.mvc.Router;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BriefUserSerializer implements JsonSerializer<RoomrUser> {

	@Override
	public JsonElement serialize(RoomrUser user, Type typeOfUser, JsonSerializationContext context) {
		String url = Router.reverse("rest.RoomrUsers.get", ImmutableMap.of("id", (Object) String.valueOf(user.id))).url;
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("name", user.name);
		return result;
	}
}