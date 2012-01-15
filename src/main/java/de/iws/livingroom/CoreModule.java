package de.iws.livingroom;

import com.google.inject.AbstractModule;

import de.iws.livingroom.application.FacadeModule;
import de.iws.livingroom.domain.OfferModule;
import de.iws.livingroom.domain.internal.reposImpl.twig.TwigRepositoryModule;
import de.iws.livingroom.infrastructure.storage.TwigModule;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FacadeModule());
		install(new OfferModule());
		install(new TwigRepositoryModule());
		install(new TwigModule());
	}

}
