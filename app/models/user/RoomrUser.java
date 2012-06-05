package models.user;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.application.RoomOfferApplication;
import models.common.Gender;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;

import org.joda.time.DateTime;
import org.joda.time.Period;

import play.db.jpa.Model;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Entity
public class RoomrUser extends Model {

	public String name;
	public String password;
	public String email;

	public Date birthdate;
	public Gender gender;

	@ManyToOne
	public Flatshare flatshare;

	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<RoomOfferApplication> applications;

	public int getAge() {
		Period period = new Period(new DateTime(birthdate), new DateTime());
		return period.getYears();
	}

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

	@Override
	public String toString() {
		ToStringHelper stringHelper = Objects.toStringHelper(this);

		stringHelper.add("name", name);
		stringHelper.add("birthdate", birthdate);
		stringHelper.add("gender", gender);

		stringHelper.add("applications", applications);
		if (flatshare != null) {
			stringHelper.add("currentFlatshare", flatshare.address);
		} else {
			stringHelper.add("currentFlatshare", "none");
		}

		return stringHelper.toString();
	}
}