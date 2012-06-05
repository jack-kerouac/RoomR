package controllers;

import javax.inject.Inject;

import models.user.RoomrUser;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.guice.InjectSupport;
import play.mvc.Router.ActionDefinition;
import controllers.formdata.InstantSearchFormData;
import controllers.formdata.RegistrationFormData;
import facade.UserFacade;

@InjectSupport
public class Registration extends AbstractRoomrController {
	@Inject
	private static UserFacade userFacade;

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

	public static void registrationForm(String redirectUrl,
			RegistrationFormData formData) {
		if (userFacade.getLoggedInUser().isPresent()) {
			// the user is already logged in and has an account in RoomR, so
			// just redirect him to his final target
			redirect(redirectUrl);
			return;
		} else {
			// Ok, go on and register the user
		}

		if (formData == null) {
			formData = new RegistrationFormData();
			InstantSearchFormData searchData = (InstantSearchFormData) Cache
					.get(session.getId() + Search.INSTANT_SEARCH_DATA_CACHE_KEY);
			if (searchData != null) {
				formData.gender = searchData.gender;
			}
		}

		render(redirectUrl, formData);
	}

	public static void register(String redirectUrl,
			@Valid RegistrationFormData formData) {
		if (validation.hasErrors()) {
			validation.keep(); // keep the errors for the next request
			Registration.registrationForm(redirectUrl, formData);
		}

		RoomrUser roomrUser = new RoomrUser();
		roomrUser.name = formData.name;
		roomrUser.email = formData.email;
		roomrUser.password = formData.password;
		roomrUser.birthdate = formData.birthdate;
		roomrUser.gender = formData.gender;

		// might throw an exception if no authentication provider user is logged
		// in, this exception is handled in parent class
		roomrUser = userFacade.createUser(roomrUser);

		Cache.delete(session.getId() + Search.INSTANT_SEARCH_DATA_CACHE_KEY);

		// this logs the user in, unfortunately I didn't find a nicer way to do
		// this...
		session.put("username", roomrUser.email);

		redirect(redirectUrl);
	}
}
