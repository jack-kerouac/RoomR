package controllers.rest;

import javax.inject.Inject;

import models.user.RoomrUser;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Controller;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import controllers.rest.dto.LoginData;
import controllers.rest.RoomrUsers;

import facade.UserFacade;
import facade.exception.LoginFailedException;

@InjectSupport
public class Security extends Controller {
	@Inject
	private static UserFacade userFacade;

	public static void login(@Valid LoginData loginData) {
		if (validation.hasErrors()) {
			badRequest();
		}

		try {
			RoomrUser user = userFacade.login(loginData.email,
					loginData.password);
			session.put("username", user.email);
			// renderJSON(ImmutableMap.of("success", true));

			RoomrUsers.get(user.id);
		} catch (LoginFailedException e) {
			renderJSON(ImmutableMap.of("error", "authFailed"));
		}
	}

	public static void logout() {
		session.clear();
		renderJSON(ImmutableMap.of("success", true));
	}
}
