package de.iws.livingroom.application;

import com.google.inject.AbstractModule;


public class FacadeModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OfferFacade.class);
		
		bind(SeekerFacade.class);
		bind(RequestFacade.class);
	}

}