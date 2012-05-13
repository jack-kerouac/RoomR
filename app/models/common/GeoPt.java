package models.common;

import javax.persistence.Embeddable;

@Embeddable
public class GeoPt {
	public float lat;
	public float lng;

	public GeoPt(float lat, float lng) {
		this.lat = lat;
		this.lng = lng;
	}
}
