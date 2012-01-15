package de.iws.livingroom.domain.internal.reposImpl.twig;

import com.google.inject.AbstractModule;

import de.iws.livingroom.domain.OfferRepository;
import de.iws.livingroom.domain.seeking.FlatshareSeekerRepository;

public class TwigRepositoryModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(FlatshareSeekerRepository.class).to(TwigFlatshareSeekerRepository.class);
		bind(OfferRepository.class).to(TwigOfferRepository.class);
	}
	
}
