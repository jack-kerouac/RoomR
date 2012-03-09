package models.internal.reposImpl.twig;

import java.util.Iterator;
import java.util.Set;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.User;
import com.google.code.twig.ObjectDatastore;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TwigRoomrUserRepository implements RoomrUserRepository {

	private final Provider<ObjectDatastore> datastoreProvider;

	@Inject
	public TwigRoomrUserRepository(Provider<ObjectDatastore> datastoreProvider) {
		this.datastoreProvider = datastoreProvider;
	}

	@Override
	public RoomrUser findUser(User gaeUser) {
		return datastoreProvider.get().load(RoomrUser.class, gaeUser.getEmail());
	}

	@Override
	public Set<RoomrUser> findAll() {
		Iterator<RoomrUser> InQuarter = datastoreProvider.get().find(RoomrUser.class);
		return ImmutableSet.copyOf(InQuarter);
	}
	
	@Override
	public void add(RoomrUser newUser) {
		datastoreProvider.get().store(newUser);
	}
	
	@Override
	public void update(RoomrUser user) {
		datastoreProvider.get().update(user);
	}
}
