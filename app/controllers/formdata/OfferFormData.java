package controllers.formdata;

import java.util.Set;

import models.common.Floor;
import models.common.Gender;
import models.flatshare.SmokingTolerance;
import play.data.validation.Match;
import play.data.validation.Range;
import play.data.validation.Required;

import com.google.common.collect.Sets;

public class OfferFormData {
	public Set<Gender> genders = Sets.newHashSet();
	public Integer minAge;
	public Integer maxAge;

	@Required
	public String street;
	public Integer streetNumber;
	@Match("[0-9]{5}")
	public String zipCode;
	@Required
	public String city;

	// initial position: Viktualienmarkt
	@Range(max = 90.0, min = -90.0)
	public float lat = 48.135848f;
	@Range(max = 180.0, min = -180.0)
	public float lng = 11.576132f;

	public boolean displayStreetView = true;
	public float streetViewHeading = 78f;
	public float streetViewPitch = 7f;
	public float streetViewZoom = 1f;
	
	public Floor floor = Floor.basement;
	public SmokingTolerance smokingTolerance = SmokingTolerance.prohibited;

	public double totalRentPerMonthInEuro;
	public double squareMeters;
}
