package modules;


import models.application.RoomOfferApplicationRepository;
import models.flatshare.FlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyFlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.notification.NotificationService;
import models.notification.PlayMailNotificationService;
import models.offer.RoomOfferRepository;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class InfrastructureModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind repository implementations
		bind(RoomOfferRepository.class).to(ObjectifyRoomOfferRepository.class);
		bind(RoomrUserRepository.class).to(ObjectifyRoomrUserRepository.class);
		bind(RoomOfferApplicationRepository.class).to(ObjectifyRoomOfferApplicationRepository.class);
		bind(FlatshareRepository.class).to(ObjectifyFlatshareRepository.class);

		bind(NotificationService.class).to(PlayMailNotificationService.class);
	}

	@Provides
	UserService provideUserService() {
		return UserServiceFactory.getUserService();
	}
}
