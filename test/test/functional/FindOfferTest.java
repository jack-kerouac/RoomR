package test.functional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;

import org.junit.Before;
import org.junit.Test;

import test.common.AbstractCoreTest;
import facade.SeekerFacade;

public class FindOfferTest extends AbstractCoreTest {

	private SeekerFacade seekerFacade;
	
	private RoomOfferRepository offerRepository;
	
	@Before
	public void setup() {
		seekerFacade = injector.getInstance(SeekerFacade.class);
		offerRepository = injector.getInstance(RoomOfferRepository.class);
	}
	
	@Test
	public void test() {
		RoomOffer offer = new RoomOffer();
		when(offerRepository.find(1l)).thenReturn(offer);
		
		RoomOffer foundOffer = seekerFacade.findOffer(1l);
		
		assertEquals(offer, foundOffer);
	}
	
}
