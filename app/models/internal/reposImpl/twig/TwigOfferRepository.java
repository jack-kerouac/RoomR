package models.internal.reposImpl.twig;

import java.util.Iterator;
import java.util.Set;

import models.Offer;
import models.OfferRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TwigOfferRepository implements OfferRepository {

	private final Provider<ObjectDatastore> datastoreProvider;

	@Inject
	public TwigOfferRepository(Provider<ObjectDatastore> datastoreProvider) {
		super();
		this.datastoreProvider = datastoreProvider;
	}

	@Override
	public void add(Offer offer) {
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
