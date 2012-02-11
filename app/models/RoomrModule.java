package models;

import play.modules.twig.Datastore;
import models.internal.reposImpl.twig.TwigOfferRepository;
import models.matching.OfferMatcher;
import models.matching.trivial.TrivialMatcher;
import models.offer.OfferRepository;

import com.google.code.twig.ObjectDatastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class RoomrModule  extends AbstractModule {

	@Override
	protected void configure() {
		// binds the TwigOfferRepository implementation to the interface OfferRepository
		bind(OfferRepository.class).to(TwigOfferRepository.class);
		
		bind(OfferMatcher.class).to(TrivialMatcher.class);
	}
	
	@Provides
	protected ObjectDatastore providesDatastore() {
		return Datastore.getInstance();
	}
	
}
