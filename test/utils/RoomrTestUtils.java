package utils;
import models.application.RoomOfferApplication;
import models.application.RoomOfferApplicationRepository;
import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyFlatshareRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

public class RoomrTestUtils {

	/**
	 * clears all repositories using the corresponding objectify implementation
	 */
	public static void clearAllObjectifyRepositories() {
		// clear flatshares
		FlatshareRepository flatshareRepo = new ObjectifyFlatshareRepository();
		for (Flatshare flatshare : flatshareRepo.findAll()) {
			flatshareRepo.remove(flatshare);
		}

		// clear RoomOffer Applications
		RoomOfferApplicationRepository applicationRepo = new ObjectifyRoomOfferApplicationRepository();
		for (RoomOfferApplication application : applicationRepo.findAll()) {
			applicationRepo.remove(application);
		}

		// clear room offers
		RoomOfferRepository roomOfferRepo = new ObjectifyRoomOfferRepository();
		for (RoomOffer roomOffer : roomOfferRepo.findAll()) {
			roomOfferRepo.remove(roomOffer);
		}

		// clear roomr user
		RoomrUserRepository userRepo = new ObjectifyRoomrUserRepository();
		for (RoomrUser user : userRepo.findAll()) {
			userRepo.remove(user);
		}
	}
}