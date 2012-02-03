package models;

import java.util.Set;

public interface OfferRepository {

	public void add(Offer offer);
	
	public Set<Offer> findAll();

}
