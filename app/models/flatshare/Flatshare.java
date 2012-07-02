package models.flatshare;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import models.common.Address;
import models.common.Floor;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class Flatshare extends ObjectifyModel {

	@Id
	public Long id;

	// LOCATION
	@Embedded
	public Address address;
	public Floor floor;

	public GeoPt geoLocation;

	@Embedded
	public StreetViewParameters streetViewParameters;

	public SmokingTolerance smokingTolerance;

	public TypeOfHouse typeOfHouse;

	public Integer numberOfRooms;

	public Set<Appliance> appliances = Sets.newLinkedHashSet();

	public Set<AdditionalSpace> additionalSpaces = Sets.newLinkedHashSet();

	public Set<RoomrUser> getResidents() {
		// TODO implement
		return new HashSet<RoomrUser>();
	}

	public RoomOffer getRoomOffer() {
		Query<RoomOffer> query = Datastore.query(RoomOffer.class).filter("flatshareId", id);
		checkState(query.countAll() == 1, "Exactly one room offer expected for a flatshare");
		return Iterables.getOnlyElement(query);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("address", address).add("geoLocation", geoLocation)
				.add("floor", floor).add("smoking tolerance", smokingTolerance).add("residents", getResidents())
				.toString();
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
