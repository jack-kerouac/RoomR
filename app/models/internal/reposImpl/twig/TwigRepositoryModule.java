package models.internal.reposImpl.twig;

import models.OfferRepository;
import models.seeking.FlatshareSeekerRepository;

import com.google.inject.AbstractModule;

public class TwigRepositoryModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(FlatshareSeekerRepository.class).to(TwigFlatshareSeekerRepository.class);
		bind(OfferRepository.class).to(TwigOfferRepository.class);
	}
	
}
