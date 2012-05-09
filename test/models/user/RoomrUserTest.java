package models.user;

import models.application.RoomOfferApplication;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferApplicationRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferRepository;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.offer.RoomOffer;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.RoomrTestUtils;

public class RoomrUserTest extends UnitTest {

	private RoomrUser userUnderTest;
	private RoomOffer roomOfferToCheck;

	@Before
	public void setUp() {
		RoomrTestUtils.clearAllObjectifyRepositories();

		// set up commonly mocks
		// user under test
		userUnderTest = new RoomrUser();
		userUnderTest.gaeUserEmail = "abc@googlemail.com";
		new ObjectifyRoomrUserRepository().add(userUnderTest);

		// roomOffer
		roomOfferToCheck = new RoomOffer();
		roomOfferToCheck.contactEmail = "inad@apimsdf.com";
		new ObjectifyRoomOfferRepository().add(roomOfferToCheck);

	}

	@Test
	public void testAppliedForExpectTrue() {

		// offer application
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.setApplicant(userUnderTest);
		mockedApplication.setRoomOffer(roomOfferToCheck);
		new ObjectifyRoomOfferApplicationRepository().add(mockedApplication);

		// verify
		assertTrue(userUnderTest.appliedFor(roomOfferToCheck));
	}

	@Test
	public void testAppliedForExpectFalse() {

		// other roomrUser that has applied for the offer
		RoomrUser userThatApplied = new RoomrUser();
		userThatApplied.gaeUserEmail = "applicant@googlemail.com";
		new ObjectifyRoomrUserRepository().add(userThatApplied);

		// offer application -> not user under test but the other user
		// is set as applicant
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.setApplicant(userThatApplied);
		mockedApplication.setRoomOffer(roomOfferToCheck);
		new ObjectifyRoomOfferApplicationRepository().add(mockedApplication);

		// verify
		assertFalse(userUnderTest.appliedFor(roomOfferToCheck));
	}
}
