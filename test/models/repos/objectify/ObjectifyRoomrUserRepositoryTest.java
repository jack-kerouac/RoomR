package models.repos.objectify;

import java.util.Date;
import java.util.Set;

import models.common.Gender;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.user.RoomrUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

import com.google.appengine.api.users.User;

public class ObjectifyRoomrUserRepositoryTest extends UnitTest {

	private ObjectifyRoomrUserRepository repositoryUnderTest;
	private RoomrUser mockedUser;

	@Before
	public void setUp() {
		repositoryUnderTest = new ObjectifyRoomrUserRepository();

		// clear repository
		Set<RoomrUser> existingUsers = repositoryUnderTest.findAll();
		for (RoomrUser roomrUser : existingUsers) {
			repositoryUnderTest.remove(roomrUser);
		}

		// set up mocked user
		mockedUser = new RoomrUser();
		mockedUser.gaeUserEmail = "user@roomr.com";
		mockedUser.gaeUser = new User("user@roomr.com", "google.com");
		mockedUser.name = "John Doe";
		mockedUser.birthdate = new Date(871908237123l);
		mockedUser.gender = Gender.male;
		// TODO set flatshare key
	}

	@After
	public void tearDown() {
		repositoryUnderTest = null;
		mockedUser = null;
	}

	@Test
	public void testStorageAndRetrieval() {
		// store mocked user instance
		repositoryUnderTest.add(mockedUser);

		// check total number of persisted roomr users
		Set<RoomrUser> allPersistedRoomrUsers = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted", 1, allPersistedRoomrUsers.size());

		// fetch previously persisted user
		RoomrUser persistedUser = repositoryUnderTest.findUser(mockedUser.gaeUser);
		assertNotNull("Couldn't retrieve previously persisted user", persistedUser);

		// check consistency
		assertEquals("User Email is not consistent for previously persisted user", mockedUser.gaeUserEmail,
				persistedUser.gaeUserEmail);
		assertEquals("Google User is not consistent for previously persisted user", mockedUser.gaeUser,
				persistedUser.gaeUser);
		assertEquals("User name is not consistent for previously persisted user", mockedUser.name, persistedUser.name);
		assertEquals("User age is not consistent for previously persisted user", mockedUser.birthdate,
				persistedUser.birthdate);
		assertEquals("User gender is not consistent for previously persisted user", mockedUser.gender,
				persistedUser.gender);

		// TODO test flatshare key

	}

	@Test
	public void testUpdate() {
		// store mocked user instance
		repositoryUnderTest.add(mockedUser);

		// update user name
		String newName = "Paul";
		mockedUser.name = newName;
		repositoryUnderTest.update(mockedUser);

		// check total number of persisted roomr users
		Set<RoomrUser> allPersistedRoomrUsers = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted", 1, allPersistedRoomrUsers.size());

		// fetch previously persisted user
		RoomrUser persistedUser = repositoryUnderTest.findUser(mockedUser.gaeUser);
		assertNotNull("Couldn't retrieve previously persisted user", persistedUser);

		// check name update
		assertEquals("Name has not been updated", newName, persistedUser.name);
	}

	@Test
	public void testRemoval() {
		// store mocked user instance
		repositoryUnderTest.add(mockedUser);

		// delete user
		repositoryUnderTest.remove(mockedUser);

		// verify deletion
		Set<RoomrUser> allPersistedRoomrUsers = repositoryUnderTest.findAll();
		assertEquals("User has not been deleted", 0, allPersistedRoomrUsers.size());
	}
}