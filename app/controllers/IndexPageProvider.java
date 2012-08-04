package controllers;

import play.mvc.Controller;

/**
 * @author Gernot Pointner Serves as a proxy for the index.html including
 *         javascript routing logic. Needed for being able to use wildcards for
 *         the uri specifiers within the routes.conf (doesn't seem to work for
 *         static files)
 */
public class IndexPageProvider extends Controller {
	public static void get() {
		renderTemplate("/public/index.html");
	}
}
