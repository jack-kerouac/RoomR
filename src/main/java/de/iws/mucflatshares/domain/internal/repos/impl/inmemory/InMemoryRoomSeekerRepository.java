package de.iws.mucflatshares.domain.internal.repos.impl.inmemory;

import java.util.HashMap;
import java.util.Map;

import de.iws.mucflatshares.domain.seeking.RoomSeeker;
import de.iws.mucflatshares.domain.seeking.RoomSeekerRepository;
import de.iws.mucflatshares.technical.annotations.ddd.DomainRepository;

//@Repository
public class InMemoryRoomSeekerRepository implements RoomSeekerRepository {

	private final Map<String, RoomSeeker> map;

	public InMemoryRoomSeekerRepository() {
		map = new HashMap<String, RoomSeeker>();
	}

	public RoomSeeker findForId(String emailAddress) {
		return map.get(emailAddress);
	}

	public void add(RoomSeeker seeker) {
		map.put(seeker.getId(), seeker);
	}

}
