package controllers;

import play.mvc.Before;
import util.DevelopmentModelDataLoader;

public class Start extends AbstractRoomrController {
	
	public static void start() {
//		Fixtures.deleteAllModels();
//		Fixtures.loadModels("dev-models.yml");
//		Map<String, Object> idCache = Fixtures.idCache;
		
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