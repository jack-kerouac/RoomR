package models;

import models.internal.reposImpl.twig.TwigOfferRepository;

import com.google.inject.AbstractModule;

public class OfferModule extends AbstractModule {

	@Override
	protected void configure() {
		// binds the TwigOfferRepository implementation to the interface OfferRepository
		bind(OfferRepository.class).to(TwigOfferRepository.class);
	}

}