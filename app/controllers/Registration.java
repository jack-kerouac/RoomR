package controllers;

import javax.inject.Inject;

import models.common.Age;
import models.user.RoomrUser;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Router.ActionDefinition;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import controllers.formdata.InstantSearchFormData;
import controllers.formdata.RegistrationFormData;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

@InjectSupport
public class Registration extends AbstractRoomrController {
	@Inject
	private static UserFacade userFacade;

	@Inject
	private static UserService userService;

	/**
	 * Creates an URL to the registration form which handles the (potential)
	 * registration of the user. The registration form will make sure to
	 * redirect the user in all cases to redirectUrl.
	 */
	public static String createRegistrationURL(String redirectUrl) {
		ActionDefinition registrationFormAction = reverse();
		{
			Registration.registrationForm(redirectUrl, null);
		}
		registrationFormAction.absolute();
		return registrationFormAction.url;
	}

	public static void registrationForm(String redirectUrl, RegistrationFormData formData) {
		User currentGaeUser = userService.getCurrentUser();

		if (currentGaeUser == null) {
			// the user is not logged in with his google account, so redirect
			// him back to the google login page
			String registrationFormURL = createRegistrationURL(redirectUrl);
			String loginURL = userService.createLoginURL(registrationFormURL);
			redirect(loginURL);
		}

		try {
			userFacade.getLoggedInUser();
			// the user is already logged in and has an account in RoomR, so
			// just redirect him to his final target
			redirect(redirectUrl);
		} catch (NoUserLoggedInException ignored) {
			// Ok, go on an register the user
		}

		if (formData == null) {
			formData = new RegistrationFormData();
			InstantSearchFormData searchData = (InstantSearchFormData) Cache.get(session.getId()
					+ Search.INSTANT_SEARCH_DATA_CACHE_KEY);
			if (searchData != null) {
				formData.age = searchData.age;
				formData.gender = searchData.gender;
			}
		}

		String nickname = currentGaeUser.getNickname();
		render(redirectUrl, formData, nickname);
	}

	public static void register(String redirectUrl, @Valid RegistrationFormData formData) {
		if (validation.hasErrors()) {
			validation.keep(); // keep the errors for the next request
			Registration.registrationForm(redirectUrl, formData);
		}

		User currentGaeUser = userService.getCurrentUser();

		if (currentGaeUser == null) {
			unauthorized();
		}

		RoomrUser roomrUser = new RoomrUser();
		roomrUser.name = formData.name;
		roomrUser.age = new Age(formData.age);
		roomrUser.gender = formData.gender;
		roomrUser.gaeUser = currentGaeUser;
		roomrUser.gaeUserEmail = currentGaeUser.getEmail();

		roomrUser = userFacade.createUser(roomrUser);

		Cache.delete(session.getId() + Search.INSTANT_SEARCH_DATA_CACHE_KEY);

		redirect(redirectUrl);
	}
}
