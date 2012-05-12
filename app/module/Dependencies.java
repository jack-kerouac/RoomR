package module;

import models.ranking.OfferRanker;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.inmemory.InmemoryMatcher;
import models.ranking.sorting.OfferSorter;
import models.ranking.sorting.OfferSorterByScore;

import com.google.inject.Binder;
import com.google.inject.Module;

import facade.AdministrationFacade;
import facade.ResidentFacade;
import facade.SeekerFacade;
import facade.UserFacade;

public class Dependencies implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(OfferRanker.class);
		binder.bind(OfferMatcher.class).to(InmemoryMatcher.class);
		binder.bind(OfferSorter.class).to(OfferSorterByScore.class);

		// bind facades
		binder.bind(SeekerFacade.class);
		binder.bind(ResidentFacade.class);
		binder.bind(AdministrationFacade.class);
		binder.bind(UserFacade.class);
	}
}
