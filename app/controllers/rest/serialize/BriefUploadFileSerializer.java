package controllers.rest.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import controllers.rest.Images;
import models.UploadFile;

import java.lang.reflect.Type;

public class BriefUploadFileSerializer implements JsonSerializer<UploadFile> {

	@Override
	public JsonElement serialize(UploadFile uploadFile, Type type, JsonSerializationContext context) {
		String url = Images.getUrlFor(uploadFile);
		JsonObject result = new JsonObject();
		result.addProperty("url", url);
		result.addProperty("name", uploadFile.name);
		return result;
	}
}