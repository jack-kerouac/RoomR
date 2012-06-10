package models.offer;

import models.flatshare.Flatshare;
import models.internal.reposImpl.objectify.ObjectifyFlatshareRepository;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import utils.RoomrTestUtils;

public class RoomOfferTest extends UnitTest {

	private RoomOffer offerUnderTest;

	@Before
	public void setUp() {
		RoomrTestUtils.clearAllObjectifyRepositories();
		offerUnderTest = new RoomOffer();
	}

	@Test(expected = IllegalStateException.class)
	public void testSetFlatshareNoId() {
		Flatshare flatshareWithoutId = new Flatshare();
		offerUnderTest.setFlatshare(flatshareWithoutId);
	}

	@Test
	public void testSetAndGetFlatshare() {
		// persist flatshare
		Flatshare flatshareWithId = new Flatshare();
		new ObjectifyFlatshareRepository().add(flatshareWithId);

		// check integrity after setting flatshare
		offerUnderTest.setFlatshare(flatshareWithId);
		assertEquals(flatshareWithId, offerUnderTest.getFlatshare());
	}

}
