package models.offer;

import java.util.Set;

import javax.persistence.Embedded;

import models.common.Age;
import models.common.Gender;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public class SeekerCriteria {

	@Embedded
	public Age minAge;
	@Embedded
	public Age maxAge;

	public Set<Gender> genders;

	public Range<Age> getAgeRange() {
		if (minAge == null && maxAge == null)
			return Ranges.all();
		else if (minAge != null && maxAge == null)
			return Ranges.atLeast(minAge);
		else if (minAge == null && maxAge != null)
			return Ranges.atMost(maxAge);
		else
			return Ranges.closed(minAge, maxAge);
	}

	public void setAgeRange(Range<Age> ageRange) {
		if (ageRange.hasLowerBound())
			minAge = ageRange.lowerEndpoint();
		if (ageRange.hasUpperBound())
			minAge = ageRange.upperEndpoint();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("genders", genders).add("age range", getAgeRange()).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genders == null) ? 0 : genders.hashCode());
		result = prime * result + ((maxAge == null) ? 0 : maxAge.hashCode());
		result = prime * result + ((minAge == null) ? 0 : minAge.hashCode());
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
		SeekerCriteria other = (SeekerCriteria) obj;
		if (genders == null) {
			if (other.genders != null)
				return false;
		} else if (!genders.equals(other.genders))
			return false;
		if (maxAge == null) {
			if (other.maxAge != null)
				return false;
		} else if (!maxAge.equals(other.maxAge))
			return false;
		if (minAge == null) {
			if (other.minAge != null)
				return false;
		} else if (!minAge.equals(other.minAge))
			return false;
		return true;
	}
}
