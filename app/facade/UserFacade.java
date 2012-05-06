package facade;

import javax.inject.Inject;

import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import facade.exception.NoAuthenticationProviderUserLoggedInException;
import facade.exception.NoUserLoggedInException;

public class UserFacade {
	@Inject
	private RoomrUserRepository userRepository;

	@Inject
	private UserService userService;

	/**
	 * Persists the given user and associates the currently logged in
	 * authentication provider user.
	 * 
	 * @param roomrUser
	 *            the user to persist.
	 * @return the persisted user.
	 * @throws NoAuthenticationProviderUserLoggedInException
	 *             If the user was not logged in to an authentication provider.
	 */
	public RoomrUser createUser(final RoomrUser roomrUser) throws NoAuthenticationProviderUserLoggedInException {
		User currentGaeUser = userService.getCurrentUser();
		if (currentGaeUser == null) {
			throw new NoAuthenticationProviderUserLoggedInException("No GAE user is logged in!");
		}

		roomrUser.gaeUser = currentGaeUser;
		roomrUser.gaeUserEmail = currentGaeUser.getEmail();

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
			throw new NoAuthenticationProviderUserLoggedInException("No GAE user is logged in!");
		}

		RoomrUser roomrUser = userRepository.findUser(currentGaeUser);
		if (roomrUser == null) {
			throw new NoUserLoggedInException("The current GAE user has no associated RoomrUser!");
		}

		return roomrUser;
	}

	/**
	 * @return <code>true</code> if a user is authenticated by an authentication
	 *         provider (like Google or Facebook). This method can return
	 *         <code>true</code> even if there is no {@link RoomrUser} for this
	 *         user yet.
	 */
	public boolean isAuthenticationProviderUserLoggedIn() {
		return userService.getCurrentUser() != null;
	}

	/**
	 * Create an URL which allows the user to log in to the underlying
	 * authentication provider. The authentication provider redirects the user
	 * after the successful login to continueUrl.
	 * 
	 * @param continueUrl
	 *            the URL to redirect the user to after successful login
	 * @return the URL which allows the user to log in at the given
	 *         authentication provider.
	 */
	public String getAuthenticationProviderLoginUrl(String continueUrl) {
		return userService.createLoginURL(continueUrl);
	}
}
