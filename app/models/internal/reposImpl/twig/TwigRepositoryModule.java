package models.internal.reposImpl.twig;

import play.modules.twig.Datastore;
import models.OfferRepository;
import models.seeking.FlatshareSeekerRepository;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.code.twig.ObjectDatastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;

public class TwigRepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
//		bind(FlatshareSeekerRepository.class).to(TwigFlatshareSeekerRepository.class);
		bind(OfferRepository.class).to(TwigOfferRepository.class);
	}
	
	@Provides
	protected ObjectDatastore provideOS() {
		return Datastore.getInstance();
	}
}
