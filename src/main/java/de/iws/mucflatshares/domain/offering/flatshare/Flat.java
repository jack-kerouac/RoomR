package de.iws.mucflatshares.domain.offering.flatshare;

import de.iws.mucflatshares.domain.common.FloorSpace;

public final class Flat {

	// TODO: infrastructure (wie weit zur S/U-Bahn, wie weit zu Bus/Tram,
	// Einkaufsmöglichkeiten)

	private final FloorSpace size;
	private final Floor floor;

	private final String houseType;

	// TV & Co
	private final String descriptionEntertainment;
	// Phone, Internet & Co
	private final String descriptionCommunication;
	private final String descriptionBathroom;

	public Flat(FloorSpace size, Floor floor, String houseType, String descriptionEntertainment,
			String descriptionCommunication, String descriptionBathroom) {
		super();
		this.size = size;
		this.floor = floor;
		this.houseType = houseType;
		this.descriptionEntertainment = descriptionEntertainment;
		this.descriptionCommunication = descriptionCommunication;
		this.descriptionBathroom = descriptionBathroom;
	}

	public FloorSpace getSize() {
		return size;
	}

	public Floor getFloor() {
		return floor;
	}

	public String getHouseType() {
		return houseType;
	}

	public String getDescriptionEntertainment() {
		return descriptionEntertainment;
	}

	public String getDescriptionCommunication() {
		return descriptionCommunication;
	}

	public String getDescriptionBathroom() {
		return descriptionBathroom;
	}

}
