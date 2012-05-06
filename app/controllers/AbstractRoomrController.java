package controllers;

import javax.inject.Inject;

import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import play.mvc.Before;
import play.mvc.Controller;
import facade.UserFacade;
import facade.exception.NoUserLoggedInException;

@InjectSupport
public abstract class AbstractRoomrController extends Controller {

	@Inject
	private static UserFacade userFacade;

	@Before
	public static void populateRenderArgs() {
		RoomrUser roomrUser = null;
		try {
			roomrUser = userFacade.getLoggedInUser();
		} catch (NoUserLoggedInException ignored) {
			// ok, no logged in user then...
		}

		renderArgs.put("user", roomrUser);
	}

}
