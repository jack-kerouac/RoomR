package de.iws.mucflatshares.domain.seeking;

import javax.persistence.Embedded;
import javax.persistence.Id;

import de.iws.mucflatshares.domain.common.PersonalProfile;

public final class RoomSeeker {

	@Id
	private String id;

	@Embedded
	private PersonalProfile profile;

	@SuppressWarnings("unused")
	private RoomSeeker() {}

	public RoomSeeker(String id, PersonalProfile profile) {
		super();
		this.id = id;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public PersonalProfile getProfile() {
		return profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RoomSeeker other = (RoomSeeker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
