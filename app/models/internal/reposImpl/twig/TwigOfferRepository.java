package models.internal.reposImpl.twig;

import java.util.Iterator;
import java.util.Set;

import models.Offer;
import models.OfferRepository;

import com.google.appengine.repackaged.com.google.common.collect.ImmutableSet;
import com.google.code.twig.ObjectDatastore;
import com.google.inject.Inject;

public class TwigOfferRepository implements OfferRepository {

	private final ObjectDatastore datastore;

	@Inject
	public TwigOfferRepository(ObjectDatastore datastore) {
		super();
		this.datastore = datastore;
	}

	@Override
	public void add(Offer offer) {
		datastore.store(offer);
	}

	@Override
	public Set<Offer> findAll() {
		Iterator<Offer> InQuarter = datastore.find(Offer.class);
		return ImmutableSet.copyOf(InQuarter);
	}

}
