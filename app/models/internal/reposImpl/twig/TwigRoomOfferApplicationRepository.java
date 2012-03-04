package models.internal.reposImpl.twig;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class TwigRoomOfferApplicationRepository implements RoomOfferApplicationRepository {

	private final Provider<ObjectDatastore> datastoreProvider;

	@Inject
	public TwigRoomOfferApplicationRepository(Provider<ObjectDatastore> datastoreProvider) {
		this.datastoreProvider = datastoreProvider;
	}

	@Override
	public void add(RoomOfferApplication newApplication) {
		datastoreProvider.get().store(newApplication);
	}
	
}
