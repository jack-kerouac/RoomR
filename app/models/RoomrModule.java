package models;

import models.application.RoomOfferApplicationRepository;
import models.flatshare.FlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyFlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.offer.RoomOfferRepository;
import models.ranking.OfferRanker;
import models.ranking.matching.OfferMatcher;
import models.ranking.matching.inmemory.InmemoryMatcher;
import models.ranking.sorting.OfferSorter;
import models.ranking.sorting.OfferSorterByScore;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import facade.AdministrationFacade;
import facade.ResidentFacade;
import facade.SeekerFacade;
import facade.UserFacade;

public class RoomrModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind repository implementations
		bind(RoomOfferRepository.class).to(ObjectifyRoomOfferRepository.class);
		bind(RoomrUserRepository.class).to(ObjectifyRoomrUserRepository.class);
		bind(RoomOfferApplicationRepository.class).to(ObjectifyRoomOfferApplicationRepository.class);
		bind(FlatshareRepository.class).to(ObjectifyFlatshareRepository.class);

		bind(OfferRanker.class);
		bind(OfferMatcher.class).to(InmemoryMatcher.class);
		bind(OfferSorter.class).to(OfferSorterByScore.class);

		bind(SeekerFacade.class);
		bind(ResidentFacade.class);
		bind(AdministrationFacade.class);
		bind(UserFacade.class);
	}

	@Provides
	UserService provideUserService() {
		return UserServiceFactory.getUserService();
	}
}
