package models.offer;

import javax.persistence.Embedded;
import javax.persistence.Id;

import models.flatshare.Flatshare;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
public final class RoomOffer extends ObjectifyModel {

	@Id
	public Long id;

	private Key<Flatshare> flatshareKey;

	@Embedded
	public RoomDetails roomDetails;
	
	@Embedded
	public SeekerCriteria criteria;

	public String description;

	public String contactEmail;

	/**
	 * Sets the flatshare for this RoomOffer. If the flatshare hasn't been
	 * persisted yet, this will be done first to obtain a valid key.
	 * 
	 * @param flatshare
	 *            the Flatshare which should be set for this user.
	 */
	public void setFlatshare(Flatshare flatshare) {
		Preconditions.checkState(flatshare.id != null, "flatshare must have been persisted before it can be set");
		Key<Flatshare> keyOfNewFlatshare;
		keyOfNewFlatshare = new Key<Flatshare>(Flatshare.class, flatshare.id);
		this.flatshareKey = keyOfNewFlatshare;
	}

	/**
	 * loads the (cached) flatshare for Room Offer from the datastore
	 * 
	 * @return the flatshare for this user
	 */
	public Flatshare getFlatshare() {
		if (this.flatshareKey == null) {
			return null;
		}
		return Datastore.find(this.flatshareKey, false);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("flatshare", getFlatshare())
				.add("room details", roomDetails).add("seeker criteria", criteria).toString();
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
