package test.core;

import models.offer.RoomOffer;

import org.junit.Before;
import org.junit.Test;

import test.common.AbstractCoreTest;
import facade.SeekerFacade;

public class FindOfferTest extends AbstractCoreTest {

	private SeekerFacade seekerFacade;

	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
	}

	@Test
	public void test() {
		RoomOffer offer = getDummyOffer();

		RoomOffer foundOffer = seekerFacade.findOffer(offer.id);

		assertEquals(offer, foundOffer);
	}

}
