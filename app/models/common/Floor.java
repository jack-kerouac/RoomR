package models.common;

/**
 * British/german numbering system for floors!
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public enum Floor {

	basement(-1), ground(0), first(1), second(2), third(3), forth(4), fifth(5), higher(6);

	private final int floorNumber;

	private Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getFloorNumber() {
		return floorNumber;
	}
}
