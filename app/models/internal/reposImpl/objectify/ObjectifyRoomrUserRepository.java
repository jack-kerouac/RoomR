package models.internal.reposImpl.objectify;

import java.util.HashSet;
import java.util.Set;

import models.flatshare.Flatshare;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.objectify.ObjectifyService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

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
		
		// add user references
		// TODO create own repo for flatshare?
		if(newUser.currentFlatshare != null){
			Key<Flatshare> flatshareKey = objectifyService.put(newUser.currentFlatshare);
			newUser.flatshareKey = flatshareKey;
		}
		objectifyService.put(newUser);
	}

	@Override
	public Set<RoomrUser> findAll() {
		Objectify objectifyService = ObjectifyService.begin();
		Query<RoomrUser> q = objectifyService.query(RoomrUser.class);
		HashSet<RoomrUser> result = new HashSet<RoomrUser>();
		for (RoomrUser roomrUser: q) {
			result.add(this.loadUserFields(roomrUser));
		}
		return result;
	}

	@Override
	public void update(RoomrUser user) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * TODO load references in getter methods of entity?
	 * does the entity then know its origin repository?
	 * loads all references for this roomr user
	 * @return the complete RoomrUser
	 */
	private RoomrUser loadUserFields(RoomrUser user){
		Objectify objectifyService = ObjectifyService.begin();
		if(user.flatshareKey != null){
			try {
				user.currentFlatshare = objectifyService.get(user.flatshareKey);
			} catch (EntityNotFoundException e) {
				// no flatshare present
			}
		}
		return user;
	}
}
