package models.flatshare;

import models.common.Address;
import models.common.Floor;
import play.data.validation.Valid;

import com.google.appengine.api.datastore.GeoPt;
import com.google.code.twig.annotation.Id;
import com.google.common.base.Objects;

public class Flatshare {

	@Id
	public long id;

	@Valid
	public Address address;

	public GeoPt geoLocation;

	public Floor floor;

	public SmokingTolerance smokingTolerance;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("address", address).add("geoLocation", geoLocation)
				.add("floor", floor).add("smoking tolerance", smokingTolerance).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flatshare other = (Flatshare) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
