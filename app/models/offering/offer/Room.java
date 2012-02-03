package models.offering.offer;

import models.common.FloorSpace;
import models.common.Furniture;

public final class Room {

	private final FloorSpace size;
	private final Furniture furniture;

	public Room(FloorSpace size, Furniture furniture) {
		super();
		this.size = size;
		this.furniture = furniture;
	}

	public Room(FloorSpace size) {
		super();
		this.size = size;
		this.furniture = new Furniture();
	}

	public FloorSpace getSize() {
		return size;
	}

	public Furniture getFurniture() {
		return furniture;
	}

	public boolean isAtLeast(FloorSpace size) {
		return this.size.isAtLeast(size);
	}

	public boolean contains(Furniture furniture) {
		return this.furniture.containsAll(furniture);
	}

}
