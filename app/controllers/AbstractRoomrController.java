package controllers;

import javax.inject.Inject;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;
import play.mvc.Before;
import play.mvc.Controller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@InjectSupport
public abstract class AbstractRoomrController extends Controller {

	@Inject
	private static RoomrUserRepository repository;

	protected static RoomrUser getCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		// user is logged into his Google Account

		if (userService.isUserLoggedIn()) {
			User currentGaeUser = userService.getCurrentUser();

			RoomrUser currentUser = repository.findUser(currentGaeUser);
			if (currentUser == null) {
				// user has not registered for RoomR yet!
				error("the user " + currentGaeUser.getEmail()
						+ " has not registered for RoomR yet, logout again here: " + userService.createLogoutURL("/"));
			}
			return currentUser;
		}

		return null;
	}

	@Before
	public static void populateRenderArgs() {
		renderArgs.put("user", getCurrentUser());
	}

}
