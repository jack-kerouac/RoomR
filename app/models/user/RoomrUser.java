package models.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.application.RoomOfferApplication;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;

import org.joda.time.DateTime;
import org.joda.time.Period;

import play.db.ebean.Model;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Entity
public class RoomrUser extends Model {

	@Id
	public String gaeUserEmail;

	public String name;

	public Date birthdate;

	public Gender gender;

	public Set<RoomOfferApplication> applications;

	public Flatshare flatshare;

	public int getAge() {
		Period period = new Period(new DateTime(birthdate), new DateTime());
		return period.getYears();
	}

	public static Finder<Long, RoomrUser> find = new Finder<Long, RoomrUser>(
			Long.class, RoomrUser.class);

	public boolean appliedFor(final RoomOffer roomOffer) {
		return Iterables.any(applications,
				new Predicate<RoomOfferApplication>() {
					@Override
					public boolean apply(RoomOfferApplication application) {
						return application.roomOffer.equals(roomOffer);
					}
				});
	}

	public boolean hasFlatshare() {
		return flatshare != null;
	}

	public static List<RoomrUser> all() {
		return find.all();
	}

	public static void create(RoomrUser roomrUser) {
		roomrUser.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	@Override
	public String toString() {
		ToStringHelper stringHelper = Objects.toStringHelper(this);

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
}