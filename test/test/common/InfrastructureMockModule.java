package test.common;


import models.application.RoomOfferApplicationRepository;
import models.flatshare.FlatshareRepository;
import models.notification.NotificationService;
import models.offer.RoomOfferRepository;
import models.user.RoomrUserRepository;

import org.mockito.Mockito;

import com.google.appengine.api.users.UserService;
import com.google.inject.AbstractModule;

public class InfrastructureMockModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind repository implementations
		bind(RoomOfferRepository.class).toInstance(Mockito.mock(RoomOfferRepository.class));
		bind(RoomrUserRepository.class).toInstance(Mockito.mock(RoomrUserRepository.class));
		bind(RoomOfferApplicationRepository.class).toInstance(Mockito.mock(RoomOfferApplicationRepository.class));
		bind(FlatshareRepository.class).toInstance(Mockito.mock(FlatshareRepository.class));
		
		// bind other services
		bind(UserService.class).toInstance(Mockito.mock(UserService.class));
		bind(NotificationService.class).toInstance(Mockito.mock(NotificationService.class));
	}

}
