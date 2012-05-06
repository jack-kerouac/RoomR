package models.repos.objectify;

import java.util.Set;

import models.application.RoomOfferApplication;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

import com.google.common.collect.Iterables;

public class ObjectifyRoomOfferApplicationRepoTest extends UnitTest {

	private ObjectifyRoomOfferApplicationRepository repositoryUnderTest;
	private RoomOfferApplication mockedApplication;

	@Before
	public void setUp() {
		repositoryUnderTest = new ObjectifyRoomOfferApplicationRepository();
		// clean repository
		for (RoomOfferApplication application : repositoryUnderTest.findAll()) {
			repositoryUnderTest.remove(application);
		}
		// create mocked Application
		mockedApplication = new RoomOfferApplication();
		mockedApplication.currentState = RoomOfferApplication.State.WAITING_FOR_INVITATION;
		mockedApplication.message = "mocked message";
		// TODO set applicant and room offer
	}

	@After
	public void tearDown() {
		repositoryUnderTest = null;
		mockedApplication = null;
	}

	@Test
	public void testStorageAndRetrieval() {
		// store mocked application
		repositoryUnderTest.add(mockedApplication);
		// check number of persisted applications
		Set<RoomOfferApplication> persistedApplications = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted", 1, persistedApplications.size());
		// check consistency of persisted application
		// TODO check applicant and room offer
		RoomOfferApplication persistedApplication = Iterables.getOnlyElement(persistedApplications);
		assertEquals("Application state is not consistent for persisted application", mockedApplication.currentState,
				persistedApplication.currentState);
		assertEquals("Application message is not consistent for persisted application", mockedApplication.message,
				persistedApplication.message);
	}

	@Test
	public void testRemoval() {
		// store mocked application
		repositoryUnderTest.add(mockedApplication);
		// ... and remove it afterwards
		repositoryUnderTest.remove(mockedApplication);

		// repository should be empty now
		Set<RoomOfferApplication> persistedApplications = repositoryUnderTest.findAll();
		assertEquals("Application has not been deleted", 0, persistedApplications.size());
	}
}
