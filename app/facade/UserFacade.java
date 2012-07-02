package facade;

import models.user.RoomrUser;

import com.google.common.base.Optional;

import controllers.Security;
import facade.exception.LoginFailedException;
import facade.exception.NoAuthenticationProviderUserLoggedInException;
import facade.exception.UserAlreadyCreatedException;

public class UserFacade {
	public RoomrUser login(String email, String password)
			throws LoginFailedException {
		RoomrUser user = RoomrUser.findByEmail(email);
		if (user == null || !user.password.equals(password)) {
			throw new LoginFailedException(email);
		}

		return user;
	}

	/**
	 * Persists the given user and associates the currently logged in
	 * authentication provider user if any.
	 * 
	 * @param roomrUser
	 *            the user to persist.
	 * @return the persisted user.
	 * @throws NoAuthenticationProviderUserLoggedInException
	 *             If the user was not logged in to an authentication provider.
	 * @throws UserAlreadyCreatedException
	 *             if a user with the given email address was already created.
	 */
	public RoomrUser createUser(final RoomrUser roomrUser)
			throws NoAuthenticationProviderUserLoggedInException,
			UserAlreadyCreatedException {
		if (RoomrUser.findByEmail(roomrUser.email) != null) {
			throw new UserAlreadyCreatedException();
		}

		return roomrUser.save();
	}

	/**
	 * @return the currently logged in {@link RoomrUser} or absent, if either no
	 *         GAE user is logged in, or the logged in GAE user has no
	 *         associated {@link RoomrUser}.
	 */
	public Optional<RoomrUser> getLoggedInUser() {
		if (!Security.isConnected()) {
			return Optional.absent();
		} else {
			return Optional.fromNullable(RoomrUser.findByEmail(Security
					.connected()));
		}
	}
}
