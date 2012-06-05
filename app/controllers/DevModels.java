package controllers;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.user.RoomrUser;
import play.modules.guice.InjectSupport;
import util.DevelopmentModelDataLoader;

@InjectSupport
public class DevModels extends AbstractRoomrController {

	@Inject
	private static DevelopmentModelDataLoader loader;

	public static void reset() {
		RoomOfferApplication.deleteAll();
		RoomOffer.deleteAll();
		RoomrUser.deleteAll();
		Flatshare.deleteAll();

		loader.loadFixtures(DevModels.class
				.getResourceAsStream("/dev-models.yml"));

		Offers.viewAll();
	}

}