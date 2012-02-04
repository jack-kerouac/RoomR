package models;

import com.google.code.twig.annotation.Id;
import com.google.common.base.Objects;

public final class Offer {
	@Id
	private Long id;

	private Location location;

	private RoomDetails roomDetails;

	public Offer() {}

	public Offer(Location location, RoomDetails roomDetails) {
		this.location = location;
		this.roomDetails = roomDetails;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public RoomDetails getRoomDetails() {
		return roomDetails;
	}

	public void setRoomDetails(RoomDetails roomDetails) {
		this.roomDetails = roomDetails;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("location", location).add("roomDetails", roomDetails).toString();
	}
}
