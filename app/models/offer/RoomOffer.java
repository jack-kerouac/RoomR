package models.offer;

import models.flatshare.Flatshare;

import com.google.code.twig.annotation.Child;
import com.google.code.twig.annotation.Id;
import com.google.common.base.Objects;

public final class RoomOffer {

	@Id
	public Long id;

	@Child
	public Flatshare flatshare;

	@Child
	public RoomDetails roomDetails;

	// a question that the flatshare can ask to applicants
	// TODO: make question(s) configurable on UI
	public String question = "Darth Vader oder Prinzessin Lea?";

	@Child
	public SeekerCriteria criteria;

	public RoomOffer() {}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("flatshare", flatshare).add("room details", roomDetails)
				.add("seeker criteria", criteria).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
