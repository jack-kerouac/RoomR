package controllers.rest;

import play.data.validation.Valid;
import play.mvc.Controller;
import controllers.rest.dto.LogMessage;

public class RoomrLogger extends Controller {
	public static void log(@Valid LogMessage message) {
		if (message == null) {
			System.out.println("Empty log message!");
			ok();
		}
		
		System.out.println("LOG from Client: [" + message.severity + "] " + message.message);
		ok();
	}
}
