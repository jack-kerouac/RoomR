package models.internal.reposImpl.objectify;

import java.util.Set;

import play.modules.objectify.ObjectifyService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Objectify;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;

public class ObjectifyRoomrUserRepository implements RoomrUserRepository {

	@Override
	public RoomrUser findUser(User gaeUser) {
		Objectify objectifyService = ObjectifyService.begin(); 
		try {
			return objectifyService.get(RoomrUser.class, gaeUser.getEmail());
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public void add(RoomrUser newUser) {
		Objectify objectifyService = ObjectifyService.begin(); 
		objectifyService.put(newUser);
	}

	@Override
	public Set<RoomrUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(RoomrUser user) {
		// TODO Auto-generated method stub
		
	}

}
