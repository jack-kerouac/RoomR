package de.iws.livingroom.application;

import java.util.Set;

import com.google.inject.Inject;

import de.iws.livingroom.domain.Location;
import de.iws.livingroom.domain.Offer;
import de.iws.livingroom.domain.OfferFactory;
import de.iws.livingroom.domain.OfferRepository;
import de.iws.livingroom.domain.RoomDetails;

public final class OfferFacade {

	private final OfferFactory factory;
	private final OfferRepository repository;

	@Inject
	private OfferFacade(OfferFactory factory, OfferRepository repository) {
		super();
		this.factory = factory;
		this.repository = repository;
	}

	public Set<Offer> findAll() {
		return repository.findAll();
	}

	public Offer create(Location location, RoomDetails roomDetails) {
		Offer newOffer = factory.create(location, roomDetails);
		repository.add(newOffer);
		return newOffer;
	}

}
