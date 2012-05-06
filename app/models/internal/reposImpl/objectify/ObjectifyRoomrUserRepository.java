package models.internal.reposImpl.objectify;

import java.util.Set;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.objectify.Datastore;
import play.modules.objectify.ObjectifyService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

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
		Key<RoomrUser> key = Datastore.put(newUser);
	}

	@Override
	public Set<RoomrUser> findAll() {
		return ImmutableSet.copyOf(Datastore.query(RoomrUser.class));
	}

	@Override
	public void update(RoomrUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(RoomrUser user) {
		// TODO Auto-generated method stub

	}
}
