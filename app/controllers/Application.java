package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import util.DevelopmentModelDataLoader;

public class Application extends Controller {
	public static void start() {
		render();
	}


	public static void createRequest() {
		render();
	}

	@Before
	public static void importDevModels() {
		// using Plays OnApplicationStart annotation does not work for
		// GAE-enabled applications, so we load the development data as soon as
		// we get the first request to the start page
		DevelopmentModelDataLoader.ensureLoaded();
	}
}
