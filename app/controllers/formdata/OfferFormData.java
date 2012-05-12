package controllers.formdata;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;

import models.common.Floor;
import models.common.Gender;
import models.flatshare.AdditionalSpace;
import models.flatshare.Appliance;
import models.flatshare.SmokingTolerance;
import models.flatshare.TypeOfHouse;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import com.google.common.collect.Sets;

public class OfferFormData implements Serializable {

	// CRITERIA

	public Set<Gender> genders = Sets.newHashSet();

	@Required
	@Min(0)
	@Max(100)
	public Integer minAge;

	@Required
	@Min(0)
	@Max(100)
	public Integer maxAge;

	// FLATSHARE

	@Required
	public String street;

	@Required
	public String streetNumber;

	@Pattern("[0-9]{5}")
	@Required
	public String zipCode;

	@Required
	public String city;

	@Required
	public Floor floor;

	// initial position: Viktualienmarkt
	@Required
	@Min(-90)
	@Max(90)
	public Float lat = 48.135848f;

	@Required
	@Min(-180)
	@Max(180)
	public Float lng = 11.576132f;

	public boolean displayStreetView = true;
	@Min(-90)
	@Max(90)
	public float streetViewLat = lat;
	@Min(-180)
	@Max(180)
	public float streetViewLng = lng;
	public float streetViewHeading = 78f;
	public float streetViewPitch = 7f;
	public float streetViewZoom = 1f;

	public SmokingTolerance smokingTolerance;

	public TypeOfHouse typeOfHouse;

	public Integer numberOfRooms;

	public Set<Appliance> appliances = Sets.newHashSet();

	public Set<AdditionalSpace> additionalSpaces = Sets.newHashSet();

	// ROOM DETAILS

	@Required
	@Min(0)
	public Double totalRentPerMonthInEuro;

	@Required
	@Min(0)
	public Double depositInEuro;

	@Required
	@Min(0)
	public Double roomSize;

	@Required
	public Date freeFrom;

	public Date freeTo;

	// DESCRIPTION

	public String description;

	// CONTACT DATA

	@Required
	@Email
	public String email;

}
