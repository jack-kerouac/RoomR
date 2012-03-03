package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.application.RoomOfferApplication;
import models.application.RoomOfferApplication.State;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;

import com.google.common.collect.Lists;

import facade.RoomOfferApplicationFacade;

@InjectSupport
public class Users extends AbstractRoomrController {

	@Inject
	private static RoomrUserRepository userRepository;

	@Inject
	private static RoomOfferApplicationFacade applicationFacade;

	public static void viewAll() {
		ArrayList<RoomrUser> users = Lists.newArrayList(userRepository.findAll());
		render(users);
	}

	public static void apply(String gaeUserEmail, long roomOfferId, RoomOfferApplication application) {
		application.currentState = State.WAITING_FOR_INVITATION;
		
		applicationFacade.apply(gaeUserEmail, roomOfferId, application.message);
	}

}
