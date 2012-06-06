package plugins;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import play.PlayPlugin;
import play.data.binding.RootParamNode;
import play.mvc.Http;
import play.mvc.Scope;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import controllers.rest.RoomrGsonBuilder;

public class JsonDataBindingPlugin extends PlayPlugin {
	private Gson gson;

	// 101:controllers.JsonDataBindingPlugin
	private Gson getGson() {
		if (gson == null) {
			gson = RoomrGsonBuilder.builder().create();
		}
		return gson;
	}

	@Override
	public Object bind(RootParamNode rootParamNode, String name,
			Class<?> clazz, Type type, Annotation[] annotations) {

		if (!Http.Request.current().contentType.equals("application/json")) {
			return null;
		}
		try {
			return getGson().fromJson(Scope.Params.current().get("body"), type);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}
}