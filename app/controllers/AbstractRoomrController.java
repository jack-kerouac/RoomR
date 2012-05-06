package controllers;

import javax.inject.Inject;

import play.modules.guice.InjectSupport;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

@InjectSupport
public abstract class AbstractRoomrController extends Controller {

	@Inject
	private static UserFacade userFacade;

	@Before
	public static void populateRenderArgs() {
		renderArgs.put("user", userFacade.getLoggedInUser().orNull());
	}

	@Catch(NoUserLoggedInException.class)
	public static void handleNoUserLoggedIn() {
		unauthorized();
	}
}
