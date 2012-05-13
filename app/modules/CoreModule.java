package modules;


import models.offer.RoomOfferTokenService;
import models.ranking.OfferRanker;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.inmemory.InmemoryMatcher;
import models.ranking.sorting.OfferSorter;
import models.ranking.sorting.OfferSorterByScore;

import com.google.inject.AbstractModule;

import facade.AdministrationFacade;
import facade.ResidentFacade;
import facade.SeekerFacade;
import facade.UserFacade;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind facades
		bind(SeekerFacade.class);
		bind(ResidentFacade.class);
		bind(AdministrationFacade.class);
		bind(UserFacade.class);

		// bind services
		bind(RoomOfferTokenService.class);
		
		bind(OfferRanker.class);
		bind(OfferMatcher.class).to(InmemoryMatcher.class);
		bind(OfferSorter.class).to(OfferSorterByScore.class);
	}
}
