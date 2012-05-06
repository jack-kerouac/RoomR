package models.internal.reposImpl.objectify;

import java.util.Set;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.objectify.Datastore;

import com.google.appengine.api.users.User;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Query;

public class ObjectifyRoomrUserRepository implements RoomrUserRepository {

	@Override
	public RoomrUser findUser(User gaeUser) {
		Query q = Datastore.query(RoomrUser.class).filter("gaeUserEmail", gaeUser.getEmail());
		if (q.countAll() == 0) {
			return null;
		}
		return Iterables.getOnlyElement(q);
	}

	@Override
	public void add(RoomrUser newUser) {
		Datastore.put(newUser);
	}

	@Override
	public Set<RoomrUser> findAll() {
		Query<RoomrUser> query = Datastore.query(RoomrUser.class);
		return ImmutableSet.copyOf(query);
	}

	@Override
	public void update(RoomrUser user) {
		Datastore.put(user);
	}

	@Override
	public void remove(RoomrUser user) {
		Datastore.delete(user);
	}
}
