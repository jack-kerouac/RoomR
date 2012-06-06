package controllers.rest.dto;

import java.util.Date;

import models.common.Gender;
import play.data.validation.Required;

public class SearchData {

	public static enum StartDateType {
		now, fixedDate;
	}

	@Required
	public String city;

	public Double minRoomSizeSquareMeters;
	public Double maxRentPerMonthInEuro;

	public Integer age;
	public Gender gender;

	public StartDateType startDateType;
	public Date startDate;

}
