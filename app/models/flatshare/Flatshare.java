package models.flatshare;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import models.common.Address;
import models.common.Floor;
import models.user.RoomrUser;
import play.modules.objectify.ObjectifyModel;

import com.google.appengine.api.datastore.GeoPt;
import com.google.common.base.Objects;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class Flatshare extends ObjectifyModel{

	@Id
	public Long id;

	// LOCATION
	@Embedded
	public Address address;

	public GeoPt geoLocation;

	@Embedded
	public MapParameters mapParameters;
	
	public Floor floor;
	
	public SmokingTolerance smokingTolerance;
	
	
	public Set<RoomrUser> getResidents(){
		// TODO implement
		return new HashSet<RoomrUser> ();
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("address", address).add("geoLocation", geoLocation)
				.add("floor", floor).add("smoking tolerance", smokingTolerance).add("residents", getResidents()).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Flatshare other = (Flatshare) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
