package de.iws.mucflatshares.domain.common;

import com.google.common.base.Preconditions;

public final class Age implements Comparable<Age> {

	private final int age;

	public Age(int age) {
		Preconditions.checkArgument(age >= 0, "age must be greater than 0");
		this.age = age;
	}

	public int getValue() {
		return age;
	}

	@Override
	public int compareTo(Age o) {
		return new Integer(age).compareTo(o.age);
	}

	@Override
	public String toString() {
		return age + " years";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
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
		if (age != other.age)
			return false;
		return true;
	}

}
