package models.user;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import models.application.RoomOfferApplication;
import models.common.Age;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyModel;

import com.google.appengine.api.users.User;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.NotSaved;

@Cached
public class RoomrUser extends ObjectifyModel {

	@Id	public String gaeUserEmail;
	public User gaeUser;

	@Embedded
	public Age age;
	
	@Embedded
	public Gender gender;

	private Key<Flatshare> flatshareKey;

	@NotSaved
	public Set<RoomOfferApplication> applications = new LinkedHashSet<RoomOfferApplication>();
	
	public Flatshare getFlatshare(){
		if(this.flatshareKey == null){
			return null;
		}
		return Datastore.find(this.flatshareKey, false);
	}
	
	public boolean appliedFor(final RoomOffer roomOffer) {
		return Iterables.any(applications, new Predicate<RoomOfferApplication>() {
			@Override
			public boolean apply(RoomOfferApplication application) {
				return application.roomOffer.equals(roomOffer);
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
		stringHelper.add("age", age);
		stringHelper.add("gender", gender);

		stringHelper.add("applications", applications);
		if(getFlatshare() != null ){
			stringHelper.add("currentFlatshare", getFlatshare().address);
		}

		return stringHelper.toString();
	}
}