package controllers.rest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

public class RoomrGsonBuilder {
	public static GsonBuilder builder() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").setPrettyPrinting()
		// SKIP ALL id fields of all classes
				.addSerializationExclusionStrategy(new ExclusionStrategy() {
					@Override
					public boolean shouldSkipField(FieldAttributes attr) {
						return false;
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						return false;
					}
				});
	}
}
