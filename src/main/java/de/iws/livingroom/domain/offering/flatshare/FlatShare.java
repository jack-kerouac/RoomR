package de.iws.livingroom.domain.offering.flatshare;

import java.util.Collection;

public class FlatShare {

	private final Flat flat;

	private final Quarter quarter;

	private final Address address;

	private final Collection<Resident> residents;
	
	public FlatShare(Flat flat, Quarter quarter, Address address, Collection<Resident> residents) {
		super();
		this.flat = flat;
		this.quarter = quarter;
		this.address = address;
		this.residents = residents;
	}

	public Flat getFlat() {
		return flat;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public Address getAddress() {
		return address;
	}

	public Collection<Resident> getResidents() {
		return residents;
	}
	
}
