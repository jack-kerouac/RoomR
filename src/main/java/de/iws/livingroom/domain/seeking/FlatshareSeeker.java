package de.iws.livingroom.domain.seeking;

import de.iws.livingroom.domain.common.PersonalProfile;

public final class FlatshareSeeker {

	private String email;

	private PersonalProfile profile;

	@SuppressWarnings("unused")
	private FlatshareSeeker() {}

	public FlatshareSeeker(String email, PersonalProfile profile) {
		super();
		this.email = email;
		this.profile = profile;
	}

	public String getEmail() {
		return email;
	}

	public PersonalProfile getProfile() {
		return profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		FlatshareSeeker other = (FlatshareSeeker) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
