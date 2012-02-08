package models.matching;

import models.matching.trivial.TrivialMatcher;

import com.google.inject.AbstractModule;

public class MatcherModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OfferMatcher.class).to(TrivialMatcher.class);
	}

}
