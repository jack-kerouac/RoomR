package controllers;

import javax.inject.Inject;

import models.user.RoomrUser;
import play.Logger;
import play.Play;
import play.Play.Mode;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.modules.guice.InjectSupport;
import util.DevelopmentModelDataLoader;

@OnApplicationStart
@InjectSupport
public class ResetDevDataStartupJob extends Job<Void> {

	@Inject
	private static DevelopmentModelDataLoader loader;

	@Override
	public void doJob() throws Exception {
		if (Play.mode == Mode.DEV) {
			if (RoomrUser.all().fetch().isEmpty()) {
				Logger.info("Resetting dev data");
				loader.loadFixtures(DevModels.class
						.getResourceAsStream("/dev-models.yml"));
			}
		}
	}
}
