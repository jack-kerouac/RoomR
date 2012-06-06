package controllers.rest.dto;

import java.util.Date;

import models.common.Gender;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Required;

public class RegistrationData {
	@Required
	public String name;
	@Required
	@Email
	public String email;
	@Required
	public String password;
	@Required
	@Equals("password")
	public String passwordVerification;

	@Required
	public Date birthdate;
	@Required
	public Gender gender;
}
