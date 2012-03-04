package controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;
import play.modules.guice.InjectSupport;

import com.google.common.collect.Lists;

@InjectSupport
public class Users extends AbstractRoomrController {

	@Inject
	private static RoomrUserRepository userRepository;

	public static void viewAll() {
		ArrayList<RoomrUser> users = Lists.newArrayList(userRepository.findAll());
		render(users);
	}

}
