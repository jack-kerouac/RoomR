package controllers;

import javax.inject.Inject;

import play.modules.guice.InjectSupport;
import facade.UserFacade;
import facade.exception.LoginFailedException;

@InjectSupport
public class Security extends controllers.Secure.Security {
	@Inject
	private static UserFacade userFacade;

	static boolean authenticate(String email, String password) {
		try {
			userFacade.login(email, password);
			return true;
		} catch (LoginFailedException e) {
			return false;
		}
	}

	// make public
	public static String connected() {
		return controllers.Secure.Security.connected();
	}

	// make public
	public static boolean isConnected() {
		return controllers.Secure.Security.isConnected();
	}
}
