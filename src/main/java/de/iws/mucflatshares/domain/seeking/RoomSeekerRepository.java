package de.iws.mucflatshares.domain.seeking;

import de.iws.mucflatshares.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface RoomSeekerRepository {

	public RoomSeeker findForId(String emailAddress);

	public void add(RoomSeeker seeker);

}
