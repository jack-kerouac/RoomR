package controllers;

import java.util.List;

import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

@InjectSupport
public class Users extends AbstractRoomrController {

	public static void viewAll() {
		List<RoomrUser> users = RoomrUser.all().fetch();
		render(users);
	}

}
