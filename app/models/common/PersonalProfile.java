package models.common;


public class PersonalProfile {

	private final Gender gender;
	private final Age age;

	public PersonalProfile(Gender gender, Age age) {
		super();
		this.gender = gender;
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public Age getAge() {
		return age;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
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
		PersonalProfile other = (PersonalProfile) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (gender != other.gender)
			return false;
		return true;
	}

}
