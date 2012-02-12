package models.offer;

import java.util.Set;

import models.common.Age;
import models.common.Gender;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;

public class SeekerCriteria {

	public Age minAge;
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

}
