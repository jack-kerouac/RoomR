package models.user;

import models.common.Age;
import models.common.Gender;
import models.user.resident.ResidentProfile;
import models.user.seeker.SeekerProfile;

import com.google.code.twig.annotation.Id;

public class RoomrUser {

	// the user id from the com.google.appengine.api.users.User object
	public String gaeUserId;

	@Id
	public String gaeUserEmail;
	
	
	public Age age;

	public Gender gender;

	public ResidentProfile residentProfile;
	
	public SeekerProfile seekerProfile;

	
	public boolean isSeeker() {
		return seekerProfile != null;
	}
	
	public boolean isResident() {
		return residentProfile != null;
	}
	
}
