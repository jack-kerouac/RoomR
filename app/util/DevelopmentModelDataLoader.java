package util;

import java.util.Map;

import javax.inject.Inject;

import models.flatshare.Flatshare;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;
import play.test.Fixtures;

@InjectSupport
public class DevelopmentModelDataLoader {

	@Inject
	private static RoomOfferRepository offerRepository;

	@Inject
	private static RoomrUserRepository userRepository;

	/**
	 * Loads all model entities in dev-models.yml if in dev mode. This method
	 * ensures that the model is loaded only once (even after application
	 * restarts).
	 */
	public static void ensureLoaded() {
		// TODO: check for DEV mode again, currently I want the data to be
		// present in Appengine as well
		// if (Play.mode == Mode.DEV && !fixturesLoaded()) {
		if (!fixturesLoaded()) {
			loadFixtures();
		}
	}

	private static boolean fixturesLoaded() {
		return Fixtures.idCache.size() > 0 || offerRepository.findAll().size() > 0;
	}

	private static void loadFixtures() {
		Fixtures.loadModels("dev-models.yml");
		Map<String, Object> idCache = Fixtures.idCache;

		for (Object o : idCache.values()) {
			if (o instanceof RoomOffer) {
				offerRepository.add((RoomOffer) o);
			} else if (o instanceof RoomrUser) {
				userRepository.add((RoomrUser) o);
			} else if (o instanceof Flatshare)
				throw new UnsupportedOperationException(
						"flatshares are persisted through 'cascade' with room offers or users");
		}
	}
}
