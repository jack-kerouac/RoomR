package models.user;

import java.util.LinkedHashSet;
import java.util.Set;

import models.application.RoomOfferApplication;
import models.common.Age;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;

import com.google.appengine.api.users.User;
import com.google.code.twig.annotation.Id;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class RoomrUser {

	@Id
	public String gaeUserEmail;
	public User gaeUser;

	public Age age;

	public Gender gender;

	public Set<RoomOfferApplication> applications = new LinkedHashSet<RoomOfferApplication>();

	public boolean appliedFor(final RoomOffer roomOffer) {
		return Iterables.any(applications, new Predicate<RoomOfferApplication>() {
			@Override
			public boolean apply(RoomOfferApplication application) {
				return application.roomOffer.equals(roomOffer);
			}
		});
	}


	public Flatshare currentFlatshare;

	@Override
	public String toString() {
		ToStringHelper stringHelper = Objects.toStringHelper(this);

		stringHelper.add("gaeUser", gaeUser);
		stringHelper.add("age", age);
		stringHelper.add("gender", gender);

		stringHelper.add("applications", applications);
		stringHelper.add("currentFlatshare", currentFlatshare);

		return stringHelper.toString();
	}

}
