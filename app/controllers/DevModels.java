package controllers;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;
import util.DevelopmentModelDataLoader;

@InjectSupport
public class DevModels extends AbstractRoomrController {

	@Inject
	private static RoomOfferRepository offerRepository;

	@Inject
	private static RoomrUserRepository userRepository;

	@Inject
	private static RoomOfferApplicationRepository applicationRepository;

	@Inject
	private static DevelopmentModelDataLoader loader;

	public static void reset() {
		for (RoomOffer offer : offerRepository.findAll())
			offerRepository.remove(offer);
		for (RoomrUser user : userRepository.findAll())
			userRepository.remove(user);
		for (RoomOfferApplication application : applicationRepository.findAll())
			applicationRepository.remove(application);

		loader.loadFixtures(DevModels.class.getResourceAsStream("/dev-models.yml"));

		Offers.viewAll();
	}

}