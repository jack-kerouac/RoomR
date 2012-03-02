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
	
	@Before
	public static void populateRenderArgs() {
		UserService userService = UserServiceFactory.getUserService();
		User currentGaeUser = userService.getCurrentUser();

		if(userService.isUserLoggedIn()) {
			// TODO: get user from datastore
			RoomrUser currentUser = repository.findUser(currentGaeUser.getEmail());
//			RoomrUser currentUser = new RoomrUser();
//			currentUser.age = new Age(25);
//			currentUser.gaeUserId = currentGaeUser.getUserId();
//			currentUser.gaeUserEmail = currentGaeUser.getEmail();
//			currentUser.gender = Gender.male;
//			currentUser.seekerProfile = new SeekerProfile();
			
			renderArgs.put("user", currentUser);
		}
	}
	
}
