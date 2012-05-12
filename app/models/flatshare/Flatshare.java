package models.flatshare;

import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import models.common.Address;
import models.common.Floor;
import models.common.GeoPt;
import models.user.RoomrUser;
import play.db.ebean.Model;

import com.google.common.base.Objects;

@Entity
public class Flatshare extends Model {

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

	public Set<Appliance> appliances;

	public Set<AdditionalSpace> additionalSpaces;

	public Set<RoomrUser> residents;

	public static Finder<Long, Flatshare> find = new Finder<Long, Flatshare>(
			Long.class, Flatshare.class);

	public static List<Flatshare> all() {
		return find.all();
	}

	public static void create(Flatshare flatshare) {
		flatshare.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("address", address).add("geoLocation", geoLocation)
				.add("floor", floor).add("smoking tolerance", smokingTolerance)
				.add("residents", residents).toString();
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
