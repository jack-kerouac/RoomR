package de.iws.livingroom.domain.internal.reposImpl.inmemory;

import java.util.HashMap;
import java.util.Map;

import de.iws.livingroom.domain.seeking.FlatshareSeeker;
import de.iws.livingroom.domain.seeking.FlatshareSeekerRepository;
import de.iws.livingroom.technical.annotations.ddd.DomainRepository;

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
