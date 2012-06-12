package models.application;

import java.util.Date;

import models.common.Gender;
import models.internal.reposImpl.objectify.ObjectifyRoomrUserRepository;
import models.user.RoomrUser;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.RoomrTestUtils;

public class RoomOfferApplicationTest extends UnitTest {
	private RoomOfferApplication roomOfferApplicationUnderTest;

	@Before
	public void setUp() {
		RoomrTestUtils.clearAllObjectifyRepositories();
		roomOfferApplicationUnderTest = new RoomOfferApplication();
	}

	//
	// public void setApplicant(RoomrUser applicant) {
	// Preconditions.checkState(applicant.gaeUserEmail != null,
	// "gae user email has to be set for the applicant");
	// this.applicantId = applicant.gaeUserEmail;
	// }
	//
	@Test(expected = IllegalStateException.class)
	public void testSetApplicantNoId() {
		RoomrUser userWithoutMail = new RoomrUser();
		roomOfferApplicationUnderTest.setApplicant(userWithoutMail);
	}

	@Test
	public void testSetAndGetUser() {
		// create associated RoomRUser and persist it
		RoomrUser user = new RoomrUser();
		user.gaeUserEmail = "dummy@dummy.com";
		user.birthdate = new Date();
		user.gender = Gender.female;
		user.name = "Hans Dampf";
		new ObjectifyRoomrUserRepository().add(user);

		// test set/get method of room offer application
		roomOfferApplicationUnderTest.setApplicant(user);
		assertTrue(user.equals(roomOfferApplicationUnderTest.getApplicant()));
	}
}
