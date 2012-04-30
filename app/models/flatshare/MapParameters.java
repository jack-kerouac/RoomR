package models.flatshare;

import com.google.appengine.api.datastore.GeoPt;

public class MapParameters {
	// STREET VIEW
	public boolean displayStreetView;

	public GeoPt streetViewGeoLocation;
	public double streetViewHeading;
	public double streetViewPitch;
	public double streetViewZoom;
}
