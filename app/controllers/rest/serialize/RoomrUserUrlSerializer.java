package controllers.rest.serialize;

import java.lang.reflect.Type;

import models.user.RoomrUser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.rest.RoomrUsers;

public class RoomrUserUrlSerializer implements JsonSerializer<RoomrUser> {

	@Override
	public JsonElement serialize(RoomrUser user, Type typeOfUser, JsonSerializationContext context) {
		String url = RoomrUsers.getUrlFor(user);
		return new JsonPrimitive(url);
	}

}