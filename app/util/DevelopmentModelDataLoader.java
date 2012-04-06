package util;

import java.util.Map;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.application.RoomOfferApplicationRepository;
import models.common.Address;
import models.common.Floor;
import models.flatshare.Flatshare;
import models.flatshare.SmokingTolerance;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.offer.SeekerCriteria;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;
import play.test.Fixtures;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

@InjectSupport
public class DevelopmentModelDataLoader {

	@Inject
	private static RoomOfferRepository offerRepository;

	@Inject
	private static RoomrUserRepository userRepository;

	@Inject
	private static RoomOfferApplicationRepository roomOfferApplicationRepository;
	
	/**
	 * Loads all model entities in dev-models.yml if in dev mode. This method ensures that the model
	 * is loaded only once (even after application restarts).
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
				RoomOffer offer = (RoomOffer) o;

				offerRepository.add(offer);
			}
			else if (o instanceof RoomrUser) {
				RoomrUser user = (RoomrUser) o;
				// TODO remove
				user.gaeUser = new User(user.gaeUserEmail, "gmail.com");
				// TODO: use flatshare from config file and set it
				Flatshare flatshare = new Flatshare();
				flatshare.address = new Address("Strasse", 12, "232", "Muenchen");
				flatshare.geoLocation = new GeoPt(48.1505f, 11.5586f);
				flatshare.floor = Floor.fifth;
				flatshare.smokingTolerance = SmokingTolerance.allowedInRoom;
				user.setFlatshare(flatshare);
				
				userRepository.add(user);
				
				// TODO get applications from config file
				RoomOfferApplication application = new RoomOfferApplication();
				application.currentState = State.WAITING_FOR_INVITATION;
				application.setApplicant(user);
				application.message ="Hope I get the flat!";
				RoomOffer offer = new RoomOffer();
				offer.setFlatshare(flatshare);
				application.setRoomOffer(offer);
				roomOfferApplicationRepository.add(application);
			}
			else if (o instanceof Flatshare)
				throw new UnsupportedOperationException(
						"flatshares are persisted through 'cascade' with room offers or users");
		}
	}
}
