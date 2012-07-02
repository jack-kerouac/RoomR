package support;

import java.util.Set;

import models.common.Address;
import models.common.Floor;
import models.common.GeoPt;
import models.flatshare.AdditionalSpace;
import models.flatshare.Appliance;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.flatshare.StreetViewParameters;
import models.flatshare.TypeOfHouse;
import models.user.RoomrUser;

import com.google.common.collect.Sets;

public final class FlatshareBuilder {

	public Address address;
	public Floor floor = Floor.basement;

	public GeoPt geoLocation = new GeoPt(48.15f, 11.5f);

	public StreetViewParameters streetViewParameters;
	public SmokingTolerance smokingTolerance = SmokingTolerance.prohibited;
	public TypeOfHouse typeOfHouse = TypeOfHouse.old;

	public Integer numberOfRooms = 3;

	public Set<Appliance> appliances = Sets.newLinkedHashSet();
	public Set<AdditionalSpace> additionalSpaces = Sets.newLinkedHashSet();

	public Set<RoomrUser> residents = Sets.newHashSet();

	private FlatshareBuilder() {
		address = new Address();
		address.city = "München";
		address.street = "Erika-Mann-Str.";
		address.streetNumber = "63";
		address.zipCode = "80333";

		streetViewParameters = new StreetViewParameters();
	}

	public static FlatshareBuilder validFlatshare() {
		return new FlatshareBuilder();
	}

	public Flatshare build() {
		Flatshare flatshare = new Flatshare();

		flatshare.address = address;
		flatshare.floor = floor;
		flatshare.geoLocation = geoLocation;
		flatshare.streetViewParameters = streetViewParameters;
		flatshare.smokingTolerance = smokingTolerance;
		flatshare.typeOfHouse = typeOfHouse;
		flatshare.numberOfRooms = numberOfRooms;
		flatshare.appliances = appliances;
		flatshare.additionalSpaces = additionalSpaces;
		flatshare.residents = residents;

		return flatshare.save();
	}

}
