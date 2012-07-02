package models.offer;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.appengine.repackaged.com.google.common.collect.Sets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class RoomOffer extends ObjectifyModel {

	public static enum State {
		PUBLIC, DELETED
	}

	@Id
	public Long id;

	public Long flatshareId;

	@Embedded
	public RoomDetails roomDetails;

	@Embedded
	public SeekerCriteria criteria;

	public String description;

	public String contactEmail;

	public State currentState;

	/**
	 * Sets the flatshare for this RoomOffer. The flatshare must have an id.
	 * 
	 * @param flatshare
	 *            the Flatshare which should be set for this user.
	 */
	public void setFlatshare(Flatshare flatshare) {
		Preconditions.checkState(flatshare.id != null, "flatshare must have been persisted before it can be set");
		this.flatshareId = flatshare.id;
	}

	/**
	 * loads the (cached) flatshare for Room Offer from the datastore
	 * 
	 * @return the flatshare for this user
	 */
	public Flatshare getFlatshare() {
		if (this.flatshareId == null) {
			return null;
		}
		return Datastore.find(Flatshare.class, flatshareId, false);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("flatshare", getFlatshare())
				.add("room details", roomDetails).add("seeker criteria", criteria).add("state", currentState)
				.toString();
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

	public Set<RoomOfferApplication> getApplications() {
		Query<RoomOfferApplication> query = Datastore.query(RoomOfferApplication.class).filter("roomOfferId", this.id);
		return Sets.newHashSet(query);
	}
}
