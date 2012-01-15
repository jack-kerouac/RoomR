package de.iws.livingroom.domain;

import com.google.inject.AbstractModule;

import de.iws.livingroom.domain.internal.reposImpl.twig.TwigOfferRepository;

public class OfferModule extends AbstractModule {

	@Override
	protected void configure() {
		// makes OfferFactory class known to Guice
		bind(OfferFactory.class);
		// binds the TwigOfferRepository implementation to the interface OfferRepository
		bind(OfferRepository.class).to(TwigOfferRepository.class);
	}

}