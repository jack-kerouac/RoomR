package models.internal.reposImpl.gdsnative;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

import models.common.Gender;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

public class GdsNativeRoomrUserRepository implements RoomrUserRepository {

	@Override
	public RoomrUser findUser(User gaeUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(RoomrUser newUser) {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Entity userEntity = new Entity("RoomrUser");
	userEntity.setProperty("gaeUserEmail", newUser.gaeUserEmail);
	userEntity.setProperty("gender",newUser.gender);
	datastore.put(userEntity);
	}

	@Override
	public Set<RoomrUser> findAll() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("RoomrUser");

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);

		List<Entity> queryResults = pq.asList(FetchOptions.Builder.withDefaults());
		// create RoomrUser Objects
		Set<RoomrUser> result = new HashSet<RoomrUser> ();
		for(Entity entity : queryResults){
			RoomrUser roomrUserFromQuery = new RoomrUser();
			roomrUserFromQuery.gaeUserEmail = (String) entity.getProperty("gaeUserEmail");
			roomrUserFromQuery.gender =  (Gender) entity.getProperty("gender");
			result.add(roomrUserFromQuery);
		}
		return result;
	}

	@Override
	public void update(RoomrUser user) {
		// TODO Auto-generated method stub
		
	}

}
