package models.common;

import javax.persistence.Embeddable;

@Embeddable
public class GeoPt {
	public float latitude;
	public float longitude;

	public GeoPt(float lat, float lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	@Override
	public String toString() {
		return "GeoPt [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitude);
		result = prime * result + Float.floatToIntBits(longitude);
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
		GeoPt other = (GeoPt) obj;
		if (Float.floatToIntBits(latitude) != Float
				.floatToIntBits(other.latitude))
			return false;
		if (Float.floatToIntBits(longitude) != Float
				.floatToIntBits(other.longitude))
			return false;
		return true;
	}
}
