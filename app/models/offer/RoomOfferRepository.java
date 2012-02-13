package models.offer;

import java.util.Set;

public interface RoomOfferRepository {

	public void add(RoomOffer offer);
	
	public Set<RoomOffer> findAll();

	public RoomOffer find(long id);

}
