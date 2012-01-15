package de.iws.livingroom.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;

public final class Location {

	/**
	 * MAXLENGTH: 20
	 */
	private String quarter;

	private Address address;

	@SuppressWarnings("unused")
	private Location() {}

	public Location(String quarter, Address address) {
		super();
		this.quarter = quarter;
		this.address = address;
	}

	public String getQuarter() {
		return quarter;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("quarter", quarter).add("address", address).toString();
	}

}
