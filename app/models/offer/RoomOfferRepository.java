package models.offer;

import java.util.Set;

public interface RoomOfferRepository {

	public void add(RoomOffer offer);

	public Set<RoomOffer> findAll();

	public RoomOffer find(long id);

	public void update(RoomOffer offer);

	public void remove(RoomOffer offer);

}
