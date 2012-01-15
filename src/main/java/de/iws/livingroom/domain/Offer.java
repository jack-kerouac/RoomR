package de.iws.livingroom.domain;

import com.google.appengine.repackaged.com.google.common.base.Objects;
import com.vercer.engine.persist.annotation.Child;
import com.vercer.engine.persist.annotation.Key;

public final class Offer {

	@Key
	private long id;

	@Child
	private Location location;

	@Child
	private RoomDetails roomDetails;

	@SuppressWarnings("unused")
	private Offer() {}

	Offer(long id, Location location, RoomDetails roomDetails) {
		this.id = id;
		this.location = location;
		this.roomDetails = roomDetails;
	}

	public long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public RoomDetails getRoomDetails() {
		return roomDetails;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("location", location).add("room details", roomDetails).toString();
	}
}
