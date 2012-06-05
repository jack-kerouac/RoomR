package models.flatshare;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import models.common.GeoPt;
import play.db.jpa.Model;

@Entity
public class StreetViewParameters extends Model {
	// STREET VIEW
	public boolean displayStreetView;
	@Embedded
	public GeoPt streetViewGeoLocation = new GeoPt(0.0f, 0.0f);
	public double streetViewHeading;
	public double streetViewPitch;
	public double streetViewZoom;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (displayStreetView ? 1231 : 1237);
		result = prime
				* result
				+ ((streetViewGeoLocation == null) ? 0 : streetViewGeoLocation
						.hashCode());
		long temp;
		temp = Double.doubleToLongBits(streetViewHeading);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(streetViewPitch);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(streetViewZoom);
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
		StreetViewParameters other = (StreetViewParameters) obj;
		if (displayStreetView != other.displayStreetView)
			return false;
		if (streetViewGeoLocation == null) {
			if (other.streetViewGeoLocation != null)
				return false;
		} else if (!streetViewGeoLocation.equals(other.streetViewGeoLocation))
			return false;
		if (Double.doubleToLongBits(streetViewHeading) != Double
				.doubleToLongBits(other.streetViewHeading))
			return false;
		if (Double.doubleToLongBits(streetViewPitch) != Double
				.doubleToLongBits(other.streetViewPitch))
			return false;
		if (Double.doubleToLongBits(streetViewZoom) != Double
				.doubleToLongBits(other.streetViewZoom))
			return false;
		return true;
	}
}