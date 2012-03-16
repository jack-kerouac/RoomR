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

	public static void registrationForm(RegistrationFormData formData) {
		UserService userService = UserServiceFactory.getUserService();
		User currentGaeUser = userService.getCurrentUser();

		if (currentGaeUser == null) {
			ActionDefinition registrationFormAction = reverse();
			{
				Registration.registrationForm(formData);
			}
			registrationFormAction.absolute();
			String loginURL = userService.createLoginURL(registrationFormAction.url);
			redirect(loginURL);
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

		render(formData);
	}

	public static void register(@Valid RegistrationFormData formData) {
		if (validation.hasErrors()) {
			validation.keep(); // keep the errors for the next request
			Registration.registrationForm(formData);
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

		// TODO: redirect to something sane
		Start.start();
	}
}
