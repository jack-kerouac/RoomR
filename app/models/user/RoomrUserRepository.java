package models.user;

import java.util.Set;

import com.google.appengine.api.users.User;


public interface RoomrUserRepository {

	public RoomrUser findUser(User gaeUser);

	public void add(RoomrUser newUser);

	public Set<RoomrUser> findAll();
	
	public void update(RoomrUser user);

	public void remove(RoomrUser user);
	
}
