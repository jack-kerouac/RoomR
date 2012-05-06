package facade;

import javax.inject.Inject;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import facade.exception.NoUserLoggedInException;

public class UserFacade {
	@Inject
	private RoomrUserRepository userRepository;

	@Inject
	private UserService userService;

	/**
	 * Persists the given user.
	 * 
	 * @param roomrUser
	 *            the user to persist.
	 * @return the persisted user.
	 */
	public RoomrUser createUser(final RoomrUser roomrUser) {
		userRepository.add(roomrUser);
		return roomrUser;
	}

	/**
	 * @return the currently logged in {@link RoomrUser}
	 * @throws NoUserLoggedInException
	 *             If either no GAE user is logged in, or the logged in GAE user
	 *             has no associated {@link RoomrUser}.
	 */
	public RoomrUser getLoggedInUser() throws NoUserLoggedInException {
		User currentGaeUser = userService.getCurrentUser();
		if (currentGaeUser == null) {
			throw new NoUserLoggedInException("No GAE user is logged in!");
		}

		RoomrUser roomrUser = userRepository.findUser(currentGaeUser);
		if (roomrUser == null) {
			throw new NoUserLoggedInException("The current GAE user has no associated RoomrUser!");
		}

		return roomrUser;
	}
}
