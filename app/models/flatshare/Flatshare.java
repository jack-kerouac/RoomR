package models.flatshare;

import models.common.Address;
import models.common.Floor;
import play.data.validation.Valid;

import com.google.common.base.Objects;

public class Flatshare {

	@Valid
	public Address address;

	public Floor floor;

	public SmokingTolerance smokingTolerance;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("address", address).add("floor", floor)
				.add("smoking tolerance", smokingTolerance).toString();
	}

}
