package models.flatshare;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.common.Address;
import models.common.Floor;
import models.common.GeoPt;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.db.jpa.Model;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

@Entity
public class Flatshare extends Model {

	// LOCATION
	@Embedded
	public Address address;
	public Floor floor;

	@Embedded
	public GeoPt geoLocation;

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	public StreetViewParameters streetViewParameters;
	public SmokingTolerance smokingTolerance;
	public TypeOfHouse typeOfHouse;

	public Integer numberOfRooms;

	@ElementCollection
	public Set<Appliance> appliances = Sets.newLinkedHashSet();
	@ElementCollection
	public Set<AdditionalSpace> additionalSpaces = Sets.newLinkedHashSet();

	@OneToMany(mappedBy = "flatshare")
	public Set<RoomrUser> residents = Sets.newHashSet();

	@OneToMany(mappedBy = "flatshare")
	public Set<RoomOffer> roomOffers = Sets.newHashSet();

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id)
				.add("address", address).add("geoLocation", geoLocation)
				.add("floor", floor).add("smoking tolerance", smokingTolerance)
				.add("residents", residents).add("roomOffers", roomOffers)
				.toString();
	}
}
