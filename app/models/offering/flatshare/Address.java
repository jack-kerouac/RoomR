package models.offering.flatshare;

public final class Address {

	private final String street;
	private final String streetNumber;

	private final int zipCode;
	private final String city;

	private final String bellLabel;

	public Address(String street, String streetNumber, int zipCode, String city, String bellLabel) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.bellLabel = bellLabel;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public int getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	public String getBellLabel() {
		return bellLabel;
	}

}
