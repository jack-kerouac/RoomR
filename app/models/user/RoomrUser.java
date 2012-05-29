package models.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import models.application.RoomOfferApplication;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;

import org.joda.time.DateTime;
import org.joda.time.Period;

import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.common.base.Predicate;
import com.google.appengine.repackaged.com.google.common.collect.Iterables;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

//@Cached
public class RoomrUser extends ObjectifyModel {

	@Id
	public String gaeUserEmail;
	public User gaeUser;

	public String name;

	public Date birthdate;

	public Gender gender;

	private Key<Flatshare> flatshareKey;

	public int getAge() {
		Period period = new Period(new DateTime(birthdate), new DateTime());
		return period.getYears();
	}

	/**
	 * loads the (cached) flatshare for this user from the datastore
	 * 
	 * @return the flatshare for this user
	 */
	public Flatshare getFlatshare() {
		// TODO (Gernot) use id as parameter for the find method
		if (this.flatshareKey == null) {
			return null;
		}
		return Datastore.find(this.flatshareKey, false);
	}

	/**
	 * Sets the flatshare for this RoomrUser. If the flatshare hasn't been
	 * persisted yet, this will be done first to obtain a valid key.
	 * 
	 * @param flatshare
	 *            the Flatshare which should be set for this user
	 */
	public void setFlatshare(Flatshare flatshare) {
		// TODO (Gernot) use preconditions to check if flatshare has a valid id
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
	 * fetches the applications from the datastore which belong to this user
	 * 
	 * @return this users's applications
	 */
	public Set<RoomOfferApplication> getApplications() {
		Set<RoomOfferApplication> result = new HashSet<RoomOfferApplication>();
		Query<RoomOfferApplication> query = Datastore.query(RoomOfferApplication.class).filter("applicantId",
				this.gaeUserEmail);

		for (RoomOfferApplication roomOfferApplication : query) {
			result.add(roomOfferApplication);
		}
		return result;
	}

	public boolean appliedFor(final RoomOffer roomOffer) {
		return Iterables.any(getApplications(), new Predicate<RoomOfferApplication>() {
			@Override
			public boolean apply(RoomOfferApplication application) {
				return application.getRoomOffer().equals(roomOffer);
			}
		});
	}

	public boolean hasFlatshare() {
		return getFlatshare() != null;
	}

	@Override
	public String toString() {
		ToStringHelper stringHelper = Objects.toStringHelper(this);

		stringHelper.add("gaeUser", gaeUser);
		stringHelper.add("name", name);
		stringHelper.add("birthdate", birthdate);
		stringHelper.add("gender", gender);

		// TODO reactivate toString method
		// stringHelper.add("applications", applications);
		// if(currentFlatshare != null)
		// stringHelper.add("currentFlatshare", currentFlatshare.address);
		// else
		// stringHelper.add("currentFlatshare", "none");

		return stringHelper.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((flatshareKey == null) ? 0 : flatshareKey.hashCode());
		result = prime * result + ((gaeUser == null) ? 0 : gaeUser.hashCode());
		result = prime * result + ((gaeUserEmail == null) ? 0 : gaeUserEmail.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		RoomrUser other = (RoomrUser) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (flatshareKey == null) {
			if (other.flatshareKey != null)
				return false;
		} else if (!flatshareKey.equals(other.flatshareKey))
			return false;
		if (gaeUser == null) {
			if (other.gaeUser != null)
				return false;
		} else if (!gaeUser.equals(other.gaeUser))
			return false;
		if (gaeUserEmail == null) {
			if (other.gaeUserEmail != null)
				return false;
		} else if (!gaeUserEmail.equals(other.gaeUserEmail))
			return false;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}