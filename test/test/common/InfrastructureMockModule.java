package test.common;

import models.notification.NotificationService;

import org.mockito.Mockito;

import com.google.inject.AbstractModule;

public class InfrastructureMockModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind other services
		bind(NotificationService.class).toInstance(
				Mockito.mock(NotificationService.class));
	}

}
