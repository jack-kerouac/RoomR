package controllers.formdata;

import models.common.Gender;
import play.data.validation.Required;

public class InstantSearchFormData {
	@Required
	public String city;

	public double minRoomSizeSquareMeters;
	public double maxRentPerMonthInEuro;

	public Integer age;
	public Gender gender;
}
