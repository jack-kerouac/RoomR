package models.user;

import models.common.Age;
import models.common.Gender;
import models.user.resident.ResidentProfile;
import models.user.seeker.SeekerProfile;

import com.google.code.twig.annotation.Id;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

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
	
	@Override
	public String toString() {
		ToStringHelper stringHelper = Objects.toStringHelper(this);
		
		stringHelper.add("email", gaeUserEmail);
		stringHelper.add("age", age);
		stringHelper.add("gender", gender);
		
		if(isSeeker())
			stringHelper.add("seekerProfile", seekerProfile);
		if(isResident())
			stringHelper.add("residentProfile", residentProfile);
		
		return stringHelper.toString();

	}
	
}
