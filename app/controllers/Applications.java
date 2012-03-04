package controllers;

import java.util.Set;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import com.google.common.base.Strings;

import facade.RoomOfferApplicationsFacade;

public class Applications extends AbstractRoomrController {

	@Inject
	private static RoomOfferApplicationsFacade applicationsFacade;

	@Inject
	private static RoomrUserRepository userRepository;

	
	public static void apply(String gaeUserEmail, long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;
		
		applicationsFacade.apply(gaeUserEmail, roomOfferId, application.message);
	}
	
	
	public static void view(String gaeUserEmail) {
		if(Strings.isNullOrEmpty(gaeUserEmail))
			notFound("no user logged in");
		
		RoomrUser user = userRepository.findUser(gaeUserEmail);
		
		if(!user.isSeeker())
			notFound("user is no seeker, thus has no applications");
		
		Set<RoomOfferApplication> applications = user.seekerProfile.applications;
		render(applications);
	}
	
}
