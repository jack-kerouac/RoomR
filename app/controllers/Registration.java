package controllers;

import javax.inject.Inject;

import models.common.Age;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Router.ActionDefinition;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import controllers.formdata.InstantSearchFormData;
import controllers.formdata.RegistrationFormData;

@InjectSupport
public class Registration extends AbstractRoomrController {
	@Inject
	private static RoomrUserRepository userRepository;

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
		UserService userService = UserServiceFactory.getUserService();
		User currentGaeUser = userService.getCurrentUser();

		if (currentGaeUser == null) {
			// the user is not logged in with his google account, so redirect
			// him back to the google login page
			String registrationFormURL = createRegistrationURL(redirectUrl);
			String loginURL = userService.createLoginURL(registrationFormURL);
			redirect(loginURL);
		}

		if (userRepository.findUser(currentGaeUser) != null) {
			// the user is already logged in and has an account in RoomR, so
			// just redirect him to his final target
			redirect(redirectUrl);
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

		UserService userService = UserServiceFactory.getUserService();
		User currentGaeUser = userService.getCurrentUser();

		if (currentGaeUser == null) {
			unauthorized();
		}

		RoomrUser roomrUser = new RoomrUser();
		roomrUser.age = new Age(formData.age);
		roomrUser.gender = formData.gender;
		roomrUser.gaeUser = currentGaeUser;
		roomrUser.gaeUserEmail = currentGaeUser.getEmail();

		userRepository.add(roomrUser);

		Cache.delete(session.getId() + Search.INSTANT_SEARCH_DATA_CACHE_KEY);

		redirect(redirectUrl);
	}
}
