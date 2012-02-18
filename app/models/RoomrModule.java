package models;

import models.internal.reposImpl.twig.TwigRoomOfferRepository;
import models.offer.RoomOfferRepository;
import models.ranking.OfferRanker;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.inmemory.InmemoryMatcher;
import models.ranking.sorting.OfferSorter;
import models.ranking.sorting.OfferSorterByScore;
import play.modules.twig.Datastore;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class RoomrModule  extends AbstractModule {

	@Override
	protected void configure() {
		// binds the TwigOfferRepository implementation to the interface OfferRepository
		bind(RoomOfferRepository.class).to(TwigRoomOfferRepository.class);
		
		bind(OfferRanker.class);
		bind(OfferMatcher.class).to(InmemoryMatcher.class);
		bind(OfferSorter.class).to(OfferSorterByScore.class);
	}
	
	@Provides
	protected ObjectDatastore providesDatastore() {
		return Datastore.getInstance();
	}
	
}
