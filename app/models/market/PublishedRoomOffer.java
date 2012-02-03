package models.market;

import models.annotations.ddd.DomainAggregateRoot;
import models.annotations.ddd.DomainEntity;
import models.offering.offer.RoomOffer;

import org.joda.time.DateTime;

@DomainAggregateRoot
@DomainEntity
public final class PublishedRoomOffer {

	private RoomOffer offer;
	private DateTime publishingDate;

	public PublishedRoomOffer(RoomOffer offer, DateTime publishingDate) {
		super();
		this.offer = offer;
		this.publishingDate = publishingDate;
	}

	public RoomOffer getOffer() {
		return offer;
	}

	public DateTime getPublishingDate() {
		return publishingDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
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
		PublishedRoomOffer other = (PublishedRoomOffer) obj;
		if (offer == null) {
			if (other.offer != null)
				return false;
		} else if (!offer.equals(other.offer))
			return false;
		return true;
	}

}
