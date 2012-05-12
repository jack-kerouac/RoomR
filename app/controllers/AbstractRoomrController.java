package controllers;

import javax.inject.Inject;

import play.mvc.Controller;
import facade.UserFacade;

public abstract class AbstractRoomrController extends Controller {

	@Inject
	private static UserFacade userFacade;

	// public static void populateRenderArgs() {
	// ctx().args.put("user", userFacade.getLoggedInUser().orNull());
	// }

	/*
	 * @Catch(NoUserLoggedInException.class) public static void
	 * handleNoUserLoggedIn() { unauthorized(); }
	 */
}
