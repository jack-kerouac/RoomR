package models.repos.objectify;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import models.common.Age;
import models.common.FloorSpace;
import models.common.Gender;
import models.internal.reposImpl.objectify.ObjectifyRoomOfferRepository;
import models.offer.RoomDetails;
import models.offer.RoomOffer;
import models.offer.SeekerCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.RoomrTestUtils;

public class ObjectifyRoomOfferRepositoryTest extends UnitTest {

	private ObjectifyRoomOfferRepository repositoryUnderTest;
	private RoomOffer mockedRoomOffer;

	@Before
	public void setUp() {
		repositoryUnderTest = new ObjectifyRoomOfferRepository();
		RoomrTestUtils.clearAllObjectifyRepositories();

		// set up mocked room offer
		RoomDetails mockedRoomDetails = new RoomDetails();
		mockedRoomDetails.freeFrom = new Date(10982733);
		mockedRoomDetails.freeTo = new Date(987);
		mockedRoomDetails.roomSize = new FloorSpace(1235);
		mockedRoomDetails.totalRentPerMonthInEuro = 435.60;
		// seeker criteria
		SeekerCriteria mockedSeekerCriteria = new SeekerCriteria();
		mockedSeekerCriteria.genders = new HashSet<Gender>();
		mockedSeekerCriteria.genders.add(Gender.female);
		mockedSeekerCriteria.minAge = new Age(25);
		mockedSeekerCriteria.maxAge = new Age(35);
		mockedRoomOffer = new RoomOffer();
		mockedRoomOffer.id = 42l;
		mockedRoomOffer.roomDetails = mockedRoomDetails;
		mockedRoomOffer.criteria = mockedSeekerCriteria;
		mockedRoomOffer.contactEmail = "example@roomr.org";
	}

	@After
	public void tearDown() {
		repositoryUnderTest = null;
	}

	@Test
	public void testStorageAndRetrieval() {

		repositoryUnderTest.add(mockedRoomOffer);

		// check total number of persisted room offers
		Set<RoomOffer> allPersistedRoomOffers = repositoryUnderTest.findAll();
		assertEquals("More or less than one entity has been persisted", 1, allPersistedRoomOffers.size());

		// try to fetch persisted room offer by id
		RoomOffer persistedOffer = repositoryUnderTest.find(mockedRoomOffer.id);
		assertTrue("Persisted Offer could not be fetched by id", mockedRoomOffer.equals(persistedOffer));

		// check consistency of persisted offer
		// TODO check associated flatshare
		assertEquals("Room Details are not consistent for the persisted flatshare entity", persistedOffer.roomDetails,
				mockedRoomOffer.roomDetails);
		assertEquals("Seeker Criteria are not consistent for the persisted flatshare entity", mockedRoomOffer.criteria,
				persistedOffer.criteria);
		assertEquals("Contact Email is not consistent for the persisted flatshare entity",
				mockedRoomOffer.contactEmail, persistedOffer.contactEmail);
	}

	@Test
	public void testRemoval() {
		// persist mocked room offer
		repositoryUnderTest.add(mockedRoomOffer);

		// delete mocked Flatshare
		repositoryUnderTest.remove(mockedRoomOffer);

		// check that repo is now empty
		Set<RoomOffer> persistedRoomOffers = repositoryUnderTest.findAll();
		assertEquals("RoomOffer has not been deleted", 0, persistedRoomOffers.size());
	}
}