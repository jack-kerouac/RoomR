package models.common;

import org.joda.time.DateTime;
import org.joda.time.Interval;


public final class Age implements Comparable<Age> {

	public int years;

	public Age() {}

	// required by SnakeYAML
	public Age(String years) {
		this(Integer.valueOf(years));
	}
	
	public Age(int years) {
		this.years = years;
	}

	public Age(DateTime birthday) {
		this.years = new Interval(birthday, new DateTime()).toPeriod().getYears();
	}

	@Override
	public int compareTo(Age o) {
		return new Integer(years).compareTo(o.years);
	}

	public boolean isLessThan(Age age) {
		return this.years < age.years;
	}

	public boolean isMoreThan(Age age) {
		return this.years > age.years;
	}

	@Override
	public String toString() {
		return years + " years";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + years;
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
		Age other = (Age) obj;
		if (years != other.years)
			return false;
		return true;
	}

}
