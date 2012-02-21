package controllers;

import play.mvc.Before;
import play.mvc.Controller;
import util.DevelopmentModelDataLoader;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Application extends Controller {
	
	public static void start() {
        UserService userService = UserServiceFactory.getUserService();
        String loginUrl = userService.createLoginURL("/");
        String logoutUrl = userService.createLogoutURL("/");
        User currentUser = userService.getCurrentUser();
		render(loginUrl, logoutUrl, currentUser);
	}

	@Before
	public static void importDevModels() {
		// using Plays OnApplicationStart annotation does not work for
		// GAE-enabled applications, so we load the development data as soon as
		// we get the first request to the start page
		DevelopmentModelDataLoader.ensureLoaded();
	}
}
