package models.internal.reposImpl.twig;

import java.util.Set;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;

import com.google.code.twig.ObjectDatastore;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
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

	@Override
	public Set<RoomOfferApplication> findAllApplicationsFor(final RoomOffer roomOffer) {
		ImmutableSet<RoomOfferApplication> allApplications = ImmutableSet.copyOf(datastoreProvider.get().find(
				RoomOfferApplication.class));

		return ImmutableSet.copyOf(Iterables.filter(allApplications, new Predicate<RoomOfferApplication>() {
			@Override
			public boolean apply(RoomOfferApplication application) {
				return application.roomOffer.equals(roomOffer);
			}
		}));
	}

	@Override
	public Set<RoomOfferApplication> findAll() {
		return ImmutableSet.copyOf(datastoreProvider.get().find(RoomOfferApplication.class));
	}

	@Override
	public void remove(RoomOfferApplication application) {
		datastoreProvider.get().delete(application);
	}

}
