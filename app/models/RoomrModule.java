package models;

import play.modules.twig.Datastore;
import models.internal.reposImpl.twig.TwigRoomOfferRepository;
import models.matching.OfferMatcher;
import models.matching.inmemory.InmemoryMatcher;
import models.offer.RoomOfferRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class RoomrModule  extends AbstractModule {

	@Override
	protected void configure() {
		// binds the TwigOfferRepository implementation to the interface OfferRepository
		bind(RoomOfferRepository.class).to(TwigRoomOfferRepository.class);
		
		bind(OfferMatcher.class).to(InmemoryMatcher.class);
	}
	
	@Provides
	protected ObjectDatastore providesDatastore() {
		return Datastore.getInstance();
	}
	
}
