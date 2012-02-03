package models.common;

import java.text.DecimalFormat;

/**
 * This class is representing an area measure for a room in square meter.
 * 
 * @author "Florian Rampp (Florian.Rampp@web.de)"
 * 
 */
public final class FloorSpace {

	private final double squareMeters;

	public FloorSpace(double squareMeters) {
		super();
		this.squareMeters = squareMeters;
	}

	public boolean isAtLeast(FloorSpace test) {
		return this.squareMeters >= test.squareMeters;
	}

	@Override
	public String toString() {
		return new DecimalFormat("#.0").format(squareMeters) + " m²";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(squareMeters);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FloorSpace other = (FloorSpace) obj;
		if (Double.doubleToLongBits(squareMeters) != Double.doubleToLongBits(other.squareMeters))
			return false;
		return true;
	}

}
