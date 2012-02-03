package models.offering.offer;

import models.annotations.ddd.DomainAggregateRoot;
import models.annotations.ddd.DomainEntity;
import models.common.LivingPeriod;
import models.common.PersonalProfile;
import models.offering.flatshare.FlatShare;
import models.offering.offer.criteria.NewResidentCriteria;

@DomainAggregateRoot
@DomainEntity
public final class RoomOffer {

	private long id;

	private FlatShare flatShare;

	private LivingPeriod freePeriod;

	private Pricing pricing;
	private Room room;

	private NewResidentCriteria newResidentCriteria;

	@SuppressWarnings("unused")
	private RoomOffer() {}
	
	public RoomOffer(long id, FlatShare flatShare, LivingPeriod freePeriod, Pricing pricing, Room room,
			NewResidentCriteria newResidentCriteria) {
		super();
		this.id = id;
		this.flatShare = flatShare;

		this.freePeriod = freePeriod;
		this.pricing = pricing;
		this.room = room;
		this.newResidentCriteria = newResidentCriteria;
	}

	public long getId() {
		return id;
	}

	public FlatShare getFlatShare() {
		return flatShare;
	}

	public LivingPeriod getFreePeriod() {
		return freePeriod;
	}

	public Room getRoom() {
		return room;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public boolean matches(PersonalProfile profile) {
		return newResidentCriteria.apply(profile);
	}

	public NewResidentCriteria getNewResidentCriteria() {
		return newResidentCriteria;
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
		RoomOffer other = (RoomOffer) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
