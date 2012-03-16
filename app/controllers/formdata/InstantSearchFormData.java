package controllers.formdata;

import java.io.Serializable;

import models.common.Gender;

public class InstantSearchFormData implements Serializable {
	public String city;

	public double minRoomSizeSquareMeters;
	public double maxRentPerMonthInEuro;

	public Integer age;
	public Gender gender;
}
