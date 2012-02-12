package models.internal.reposImpl.twig;

import java.util.Iterator;
import java.util.Set;

import models.offer.Offer;
import models.offer.OfferRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TwigOfferRepository implements OfferRepository {

	private final Provider<ObjectDatastore> datastoreProvider;

	// TODO: replace this counter
	private static long offerIdCounter = 1;
	
	@Inject
	public TwigOfferRepository(Provider<ObjectDatastore> datastoreProvider) {
		super();
		this.datastoreProvider = datastoreProvider;
	}

	@Override
	public void add(Offer offer) {
		if(offer.id == null)
			offer.id = offerIdCounter++;
		datastoreProvider.get().store(offer);
	}

	@Override
	public Set<Offer> findAll() {
		Iterator<Offer> InQuarter = datastoreProvider.get().find(Offer.class);
		return ImmutableSet.copyOf(InQuarter);
	}
	
	@Override
	public Offer find(long id) {
		return datastoreProvider.get().load(Offer.class, id);
	}

}
