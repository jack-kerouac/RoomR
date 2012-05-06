package models.offer;

import javax.persistence.Embedded;
import javax.persistence.Id;

import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import models.flatshare.Flatshare;

import com.google.code.twig.annotation.Child;
import com.google.common.base.Objects;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.NotSaved;


@Cached
public final class RoomOffer extends ObjectifyModel{

	@Id
	public Long id;

	private Key<Flatshare> flatshareKey;

	@Embedded
	public RoomDetails roomDetails;

	// a question that the flatshare can ask to applicants
	// TODO: make question(s) configurable on UI
	public String question = "Darth Vader oder Prinzessin Lea?";


	@Embedded
	public SeekerCriteria criteria;


	
	/**
	 * Sets the flatshare for this RoomOffer. If the flatshare hasn't been persisted yet,
	 * this will be done first to obtain a valid key.
	 * @param flatshare the Flatshare which should be set for this user.
	 */
	public void setFlatshare(Flatshare flatshare) {
		Key<Flatshare> keyOfNewFlatshare;
		if (flatshare.id == null) {
			// flatshare has to be persisted first to obtain a valid key
			keyOfNewFlatshare = Datastore.put(flatshare);
		} else {
			keyOfNewFlatshare = new Key<Flatshare>(Flatshare.class, flatshare.id);
		}
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
		return Objects.toStringHelper(this).add("flatshare", getFlatshare()).add("room details", roomDetails)
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
