package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import models.common.Gender;
import models.user.RoomrUser;
import models.user.RoomrUserRepository;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import play.test.UnitTest;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

import facade.exception.NoAuthenticationProviderUserLoggedInException;

public class UserFacadeTest extends UnitTest {
	private UserFacade userFacade;

	private RoomrUserRepository userRepository;
	private UserService userService;

	@Before
	public void setUp() {
		userRepository = mock(RoomrUserRepository.class);
		userService = mock(UserService.class);

		userFacade = new UserFacade();
		userFacade.setUserRepository(userRepository);
		userFacade.setUserService(userService);
	}

	@Test(expected = NoAuthenticationProviderUserLoggedInException.class)
	public void testCreateUserNoGAEUser() throws NoAuthenticationProviderUserLoggedInException {
		when(userService.getCurrentUser()).thenReturn(null);
		verifyZeroInteractions(userRepository);

		RoomrUser roomrUser = createRoomrUser();

		userFacade.createUser(roomrUser);
	}

	@Test
	public void testCreateUser() throws Exception {
		User gaeUser = new User("test@test.de", "test");
		when(userService.getCurrentUser()).thenReturn(gaeUser);

		RoomrUser roomrUser = createRoomrUser();
		RoomrUser createdUser = userFacade.createUser(roomrUser);

		ArgumentCaptor<RoomrUser> userCaptor = ArgumentCaptor.forClass(RoomrUser.class);
		verify(userRepository).add(userCaptor.capture());
		verifyNoMoreInteractions(userRepository);

		RoomrUser persistedUser = userCaptor.getValue();
		assertEquals(persistedUser, createdUser);
		assertEquals("test@test.de", createdUser.gaeUserEmail);
		assertEquals(gaeUser, createdUser.gaeUser);
	}

	private RoomrUser createRoomrUser() {
		RoomrUser roomrUser = new RoomrUser();
		roomrUser.name = "Test";
		roomrUser.birthdate = new DateTime(0).toDate();
		roomrUser.gender = Gender.male;
		return roomrUser;
	}
}
