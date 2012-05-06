package models.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.code.twig.annotation.Embedded;
import com.google.common.base.Preconditions;

@Embedded
public final class Address {
	public String street;
	public String streetNumber;
	public String zipCode;
	public String city;

	public Address() {}

	public Address(String street, String streetNumber, String zipCode, String city) {
		Preconditions.checkArgument(StringUtils.isNotBlank(street));
		Preconditions.checkArgument(StringUtils.isNotBlank(zipCode) && NumberUtils.toInt(zipCode) > 0
				&& NumberUtils.toInt(zipCode) < 100000);
		Preconditions.checkArgument(StringUtils.isNotBlank(streetNumber));
		Preconditions.checkArgument(StringUtils.isNotBlank(city));

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

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
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
