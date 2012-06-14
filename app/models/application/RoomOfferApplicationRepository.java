package models.application;

import java.util.Set;

public interface RoomOfferApplicationRepository {

	public void add(RoomOfferApplication newApplication);

	public Set<RoomOfferApplication> findAll();

	public void remove(RoomOfferApplication application);

	void update(RoomOfferApplication application);
}
