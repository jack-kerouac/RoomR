package models.seeking;

import models.annotations.ddd.DomainRepository;

@DomainRepository
public interface FlatshareSeekerRepository {

	public FlatshareSeeker findForId(String emailAddress);

	public void add(FlatshareSeeker seeker);

}
