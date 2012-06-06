package controllers.rest.dto;

import play.data.validation.Email;
import play.data.validation.Required;

public class LoginData {
	@Required
	@Email
	public String email;

	@Required
	public String password;
}
