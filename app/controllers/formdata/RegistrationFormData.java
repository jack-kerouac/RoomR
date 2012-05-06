package controllers.formdata;

import java.io.Serializable;

import models.common.Gender;
import play.data.validation.Required;

public class RegistrationFormData implements Serializable {
	@Required
	public String name;

	@Required
	public Integer age;
	@Required
	public Gender gender;
}
