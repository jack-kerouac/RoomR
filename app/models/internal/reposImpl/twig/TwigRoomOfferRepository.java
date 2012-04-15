package models.internal.reposImpl.twig;

import java.util.Iterator;
import java.util.Set;

import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TwigRoomOfferRepository implements RoomOfferRepository {

	private final Provider<ObjectDatastore> datastoreProvider;

	// TODO: replace this counter
	private static long offerIdCounter = 1;
	
	@Inject
	public TwigRoomOfferRepository(Provider<ObjectDatastore> datastoreProvider) {
		super();
		this.datastoreProvider = datastoreProvider;
	}

	@Override
	public void add(RoomOffer offer) {
		offerIdCounter++;
		if(offer.id == null)
			offer.id = offerIdCounter;
		datastoreProvider.get().store(offer);
	}

	@Override
	public Set<RoomOffer> findAll() {
		Iterator<RoomOffer> InQuarter = datastoreProvider.get().find(RoomOffer.class);
		return ImmutableSet.copyOf(InQuarter);
	}
	
	@Override
	public RoomOffer find(long id) {
		return datastoreProvider.get().load(RoomOffer.class, id);
	}
	
	@Override
	public void remove(RoomOffer offer) {
		datastoreProvider.get().delete(offer);
	}

}
