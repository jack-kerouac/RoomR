package models.common;

import play.data.validation.Min;

import com.google.common.base.Preconditions;

public final class Age implements Comparable<Age> {

	@Min(0)
	public int years;

	public Age() {}
	
	public Age(int years) {
		this.years = years;
	}

	@Override
	public int compareTo(Age o) {
		return new Integer(years).compareTo(o.years);
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
