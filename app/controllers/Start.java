package controllers;

import javax.inject.Inject;

import models.common.Gender;
import models.user.RoomrUser;
import play.mvc.Result;

import com.google.common.base.Optional;

import facade.UserFacade;

public class Start extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;

	public static Result start() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();

		Integer age = null;
		Gender gender = null;

		if (loggedInUser.isPresent()) {
			age = loggedInUser.get().getAge();
			gender = loggedInUser.get().gender;
		}

		return ok(views.html.Start.start.render(age, gender));
	}

}
