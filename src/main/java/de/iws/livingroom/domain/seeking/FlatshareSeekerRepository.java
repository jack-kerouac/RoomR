package de.iws.livingroom.domain.seeking;

import de.iws.livingroom.technical.annotations.ddd.DomainRepository;

@DomainRepository
public interface FlatshareSeekerRepository {

	public FlatshareSeeker findForId(String emailAddress);

	public void add(FlatshareSeeker seeker);

}
