package facade;

import models.user.RoomrUser;

public class UserIsNotSeekerException extends Exception {

	private final RoomrUser user;

	public UserIsNotSeekerException(RoomrUser user) {
		super("user " + user + " is no seeker");
		this.user = user;
	}

	public RoomrUser getUser() {
		return user;
	}

}
