package controllers.formdata;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import models.common.Floor;
import models.common.Gender;
import models.flatshare.SmokingTolerance;
import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.Min;
import play.data.validation.Range;
import play.data.validation.Required;

import com.google.common.collect.Sets;

public class OfferFormData implements Serializable {
	
	// CRITERIA
	
	public Set<Gender> genders = Sets.newHashSet();
	
	@Required
	@Range(min=0, max=100)
	public Integer minAge;
	
	@Required
	@Range(min=0, max=100)
	public Integer maxAge;

	
	// FLATSHARE
	
	@Required
	public String street;
	
	@Required
	public String streetNumber;

	@Match("[0-9]{5}")
	@Required
	public String zipCode;
	
	@Required
	public String city;

	@Required
	public Floor floor;
	
	// initial position: Viktualienmarkt
	@Range(max = 90.0, min = -90.0)
	@Required
	public Float lat = 48.135848f;
	
	@Range(max = 180.0, min = -180.0)
	@Required
	public Float lng = 11.576132f;
	
	
	public boolean displayStreetView = true;
	@Range(max = 90.0, min = -90.0)
	public float streetViewLat = lat;
	@Range(max = 180.0, min = -180.0)
	public float streetViewLng = lng;
	public float streetViewHeading = 78f;
	public float streetViewPitch = 7f;
	public float streetViewZoom = 1f;
	
	public SmokingTolerance smokingTolerance = SmokingTolerance.prohibited;
	
	
	// ROOM DETAILS


	@Required
	@Min(0.0)
	public Double totalRentPerMonthInEuro;
	
	@Required
	@Min(0.0)
	public Double roomSize;
	
	@Required
	public Date freeFrom;
	
	public Date freeTo;

	
	// CONTACT DATA
	
	
	@Required
	@Email
	public String email;
	
	
}
