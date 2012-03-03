package models.user;

import java.util.Set;


public interface RoomrUserRepository {

	public RoomrUser findUser(String gaeEmailAddress);

	public void add(RoomrUser newUser);

	public Set<RoomrUser> findAll();
	
	public void update(RoomrUser user);
	
}
