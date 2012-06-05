package modules;

import models.notification.NotificationService;
import models.notification.PlayMailNotificationService;

import com.google.inject.AbstractModule;

public class InfrastructureModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NotificationService.class).to(PlayMailNotificationService.class);
	}
}
