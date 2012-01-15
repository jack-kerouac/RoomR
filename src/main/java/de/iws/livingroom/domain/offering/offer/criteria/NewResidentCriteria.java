package de.iws.livingroom.domain.offering.offer.criteria;

import com.google.common.base.Predicate;

import de.iws.livingroom.domain.common.PersonalProfile;

public final class NewResidentCriteria implements Predicate<PersonalProfile> {

	public final GenderCriteria genders;

	public final AgeCriteria ages;

	public NewResidentCriteria(GenderCriteria genders, AgeCriteria ages) {
		super();
		this.genders = genders;
		this.ages = ages;
	}

	@Override
	public boolean apply(PersonalProfile profile) {
		return genders.apply(profile.getGender()) && ages.apply(profile.getAge());
	}

	public GenderCriteria getGenders() {
		return genders;
	}

	public AgeCriteria getAges() {
		return ages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ages == null) ? 0 : ages.hashCode());
		result = prime * result + ((genders == null) ? 0 : genders.hashCode());
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
		NewResidentCriteria other = (NewResidentCriteria) obj;
		if (ages == null) {
			if (other.ages != null)
				return false;
		} else if (!ages.equals(other.ages))
			return false;
		if (genders == null) {
			if (other.genders != null)
				return false;
		} else if (!genders.equals(other.genders))
			return false;
		return true;
	}

}
