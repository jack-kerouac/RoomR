package controllers.rest.serialize;

import java.lang.reflect.Type;

import play.db.jpa.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ModelIdSerializer implements JsonSerializer<Model> {

	@Override
	public JsonElement serialize(Model model, Type typeOfUser,
			JsonSerializationContext context) {
		return new JsonPrimitive(model.id);
	}
}