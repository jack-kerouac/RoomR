package util;

import java.util.Map;

import javax.inject.Inject;

import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import play.Play;
import play.Play.Mode;
import play.modules.guice.InjectSupport;
import play.test.Fixtures;

@InjectSupport
public class DevelopmentModelDataLoader {
	@Inject
	private static RoomOfferRepository offerRepository;

	/**
	 * Loads all model entities in dev-models.yml if in dev mode. This method
	 * ensures that the model is loaded only once (even after application
	 * restarts).
	 */
	public static void ensureLoaded() {
		if (Play.mode == Mode.DEV && !fixturesLoaded()) {
			loadFixtures();
		}
	}

	private static boolean fixturesLoaded() {
		return Fixtures.idCache.size() > 0
				|| offerRepository.findAll().size() > 0;
	}

	private static void loadFixtures() {
		Fixtures.loadModels("dev-models.yml");
		Map<String, Object> idCache = Fixtures.idCache;

		for (Object o : idCache.values()) {
			if (o instanceof RoomOffer) {
				offerRepository.add((RoomOffer) o);
			}
		}
	}
}
