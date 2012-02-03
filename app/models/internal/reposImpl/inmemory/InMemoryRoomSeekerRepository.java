package models.internal.reposImpl.inmemory;

import java.util.HashMap;
import java.util.Map;

import models.seeking.FlatshareSeeker;
import models.seeking.FlatshareSeekerRepository;

//@Repository
public class InMemoryRoomSeekerRepository implements FlatshareSeekerRepository {

	private final Map<String, FlatshareSeeker> map;

	public InMemoryRoomSeekerRepository() {
		map = new HashMap<String, FlatshareSeeker>();
	}

	public FlatshareSeeker findForId(String emailAddress) {
		return map.get(emailAddress);
	}

	public void add(FlatshareSeeker seeker) {
		map.put(seeker.getEmail(), seeker);
	}

}
