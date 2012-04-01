package models;

import models.application.RoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.internal.reposImpl.twig.TwigRoomOfferRepository;
import models.offer.RoomOfferRepository;
import models.ranking.OfferRanker;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.inmemory.InmemoryMatcher;
import models.ranking.sorting.OfferSorter;
import models.ranking.sorting.OfferSorterByScore;
import models.user.RoomrUserRepository;
import play.modules.twig.Datastore;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import facade.RoomOfferApplicationsFacade;

public class RoomrModule extends AbstractModule {

	@Override
	protected void configure() {
		// binds the TwigOfferRepository implementation to the interface
		// OfferRepository
		bind(RoomOfferRepository.class).to(TwigRoomOfferRepository.class);
		bind(RoomrUserRepository.class).to(ObjectifyRoomrUserRepository.class);
		bind(RoomOfferApplicationRepository.class).to(ObjectifyRoomOfferApplicationRepository.class);

		bind(OfferRanker.class);
		bind(OfferMatcher.class).to(InmemoryMatcher.class);
		bind(OfferSorter.class).to(OfferSorterByScore.class);

		bind(RoomOfferApplicationsFacade.class);
	}

	@Provides
	protected ObjectDatastore providesDatastore() {
		return Datastore.getInstance();
	}

}
