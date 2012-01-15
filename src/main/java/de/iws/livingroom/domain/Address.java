package de.iws.livingroom.domain;

public final class Address {

	private String street;
	private int streetNumber;
	private int zipCode;
	private String city;

	@SuppressWarnings("unused")
	private Address() {}

	public Address(String street, int streetNumber, int zipCode, String city) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public int getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return street + " " + streetNumber + ", " + zipCode + " " + city;
	}

}
