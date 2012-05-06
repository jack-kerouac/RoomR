package controllers;

import javax.inject.Inject;

import models.common.Gender;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;

import com.google.common.base.Optional;

import facade.UserFacade;

@InjectSupport
public class Start extends AbstractRoomrController {

	@Inject
	private static UserFacade userFacade;

	public static void start() {
		Optional<RoomrUser> loggedInUser = userFacade.getLoggedInUser();

		Integer age = null;
		Gender gender = null;

		if (loggedInUser.isPresent()) {
			age = loggedInUser.get().getAge();
			gender = loggedInUser.get().gender;
		}

		render(age, gender);
	}

}
