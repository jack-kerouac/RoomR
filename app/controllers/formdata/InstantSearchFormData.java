package controllers.formdata;

import java.io.Serializable;
import java.util.Date;

import models.common.Gender;
import play.data.validation.Required;

public class InstantSearchFormData implements Serializable {

	@Required
	public String city;

	public Double minRoomSizeSquareMeters;
	public Double maxRentPerMonthInEuro;

	public Integer age;
	public Gender gender;

	public Date startDate;

}
