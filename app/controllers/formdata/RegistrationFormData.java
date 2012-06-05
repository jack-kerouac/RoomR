package controllers.formdata;

import java.io.Serializable;
import java.util.Date;

import models.common.Gender;
import play.data.validation.Required;

public class RegistrationFormData implements Serializable {
	@Required
	public String name;
	@Required
	public String email;
	@Required
	public String password;

	@Required
	public Date birthdate;
	@Required
	public Gender gender;
}
