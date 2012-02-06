package models;

import play.data.validation.MaxSize;
import play.data.validation.Valid;

import com.google.appengine.repackaged.com.google.common.base.Objects;

public final class Location {

	@MaxSize(3)
	public String quarter;

	@Valid
	public Address address;

	public Location() {
	}

	public Location(String quarter, Address address) {
		super();
		this.quarter = quarter;
		this.address = address;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("quarter", quarter)
				.add("address", address).toString();
	}

}
