package models.common;

import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.common.base.Preconditions;

@Embeddable
public final class Address {
	public String street;
	public String streetNumber;
	public String zipCode;
	public String city;

	public Address() {
	}

	public Address(String street, String streetNumber, String zipCode,
			String city) {
		Preconditions.checkArgument(StringUtils.isNotBlank(street));
		Preconditions.checkArgument(StringUtils.isNotBlank(zipCode)
				&& NumberUtils.toInt(zipCode) > 0
				&& NumberUtils.toInt(zipCode) < 100000);
		Preconditions.checkArgument(StringUtils.isNotBlank(streetNumber));
		Preconditions.checkArgument(StringUtils.isNotBlank(city));

		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
	}

	@Override
	public String toString() {
		return street + " " + streetNumber + ", " + zipCode + " " + city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result
				+ ((streetNumber == null) ? 0 : streetNumber.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (streetNumber == null) {
			if (other.streetNumber != null)
				return false;
		} else if (!streetNumber.equals(other.streetNumber))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
}
