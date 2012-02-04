package models;

import com.google.appengine.repackaged.com.google.common.base.Objects;

public final class Location {

	/**
	 * MAXLENGTH: 20
	 */
	private String quarter;

	private Address address;

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
