package controllers;

import java.util.List;

import models.user.RoomrUser;
import play.mvc.Result;

public class Users extends AbstractRoomrController {
	public static Result viewAll() {
		List<RoomrUser> users = RoomrUser.all();
		// render(users);
		return ok("");
	}

}
