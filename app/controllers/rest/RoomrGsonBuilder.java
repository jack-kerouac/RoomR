package controllers.rest;

import com.google.gson.GsonBuilder;

public class RoomrGsonBuilder {
	public static GsonBuilder builder() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").setPrettyPrinting();
	}
}
