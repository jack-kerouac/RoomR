package de.iws.livingroom.infrastructure.trials.appengine.twig.model;

import java.util.Date;

public abstract class Photo {

	private Date dateTaken;

	private long exposureMs;

	public Date getDateTaken() {
		return dateTaken;
	}

	public Photo setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
		return this;
	}

	public long getExposureMs() {
		return exposureMs;
	}

	public Photo setExposureMs(long exposureMs) {
		this.exposureMs = exposureMs;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTaken == null) ? 0 : dateTaken.hashCode());
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
		Photo other = (Photo) obj;
		if (dateTaken == null) {
			if (other.dateTaken != null)
				return false;
		} else if (!dateTaken.equals(other.dateTaken))
			return false;
		return true;
	}

}
