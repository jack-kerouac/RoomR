package de.iws.livingroom.domain;

import java.util.Set;

public interface OfferRepository {

	public void add(Offer offer);
	
	public Set<Offer> findAll();

}
