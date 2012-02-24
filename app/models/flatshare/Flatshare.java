package models.flatshare;

import models.common.Address;
import models.common.Floor;
import play.data.validation.Valid;

import com.google.appengine.api.datastore.GeoPt;
import com.google.common.base.Objects;

public class Flatshare {

	@Valid
	public Address address;

	public GeoPt geoLocation;

	public Floor floor;

	public SmokingTolerance smokingTolerance;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("address", address)
				.add("geoLocation", geoLocation).add("floor", floor)
				.add("smoking tolerance", smokingTolerance).toString();
	}

}
