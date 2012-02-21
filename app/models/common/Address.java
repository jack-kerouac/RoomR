package models.common;

import play.data.validation.Match;
import play.data.validation.Required;

import com.google.code.twig.annotation.Embedded;

@Embedded
public final class Address {
	@Required
	public String street;
	public int streetNumber;
	@Match("[0-9]{5}")
	public String zipCode;
	public String city;

	@SuppressWarnings("unused")
	public Address() {}

	public Address(String street, int streetNumber, String zipCode, String city) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return street + " " + streetNumber + ", " + zipCode + " " + city;
	}

}
