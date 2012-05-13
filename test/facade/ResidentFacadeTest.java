package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import models.flatshare.Flatshare;
import models.flatshare.FlatshareRepository;
import models.offer.RoomOffer;
import models.offer.RoomOfferRepository;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import facade.exception.RoomOfferUpdateException;

public class ResidentFacadeTest extends UnitTest {

	private FlatshareRepository flatshareRepository;
	private RoomOfferRepository roomOfferRepository;
	private ResidentFacade residentFacade;
	private RoomOffer mockedRoomOffer;
	private Flatshare mockedFlatshare;

	@Before
	public void setUp() {
		flatshareRepository = mock(FlatshareRepository.class);
		roomOfferRepository = mock(RoomOfferRepository.class);
		residentFacade = new ResidentFacade();
		residentFacade.setFlatshareRepository(flatshareRepository);
		residentFacade.setRoomOfferRepository(roomOfferRepository);
		mockedFlatshare = new Flatshare();
		mockedFlatshare.id = 12l;
		mockedRoomOffer = mock(RoomOffer.class);
		when(mockedRoomOffer.getFlatshare()).thenReturn(mockedFlatshare);
	}

	/**
	 * test the update room offer method when the given flatshare doesn't belong
	 * to the room offer
	 * 
	 * @throws RoomOfferUpdateException
	 */
	@Test(expected = RoomOfferUpdateException.class)
	public void testUpdateRoomOfferForIncorrectFlatshare() throws RoomOfferUpdateException {
		// set up mocks
		Flatshare otherFlatshare = new Flatshare();
		otherFlatshare.id = mockedFlatshare.id + 1;

		// trigger method
		residentFacade.updateRoomOfferForFlatshare(mockedRoomOffer, otherFlatshare);

		// verify
		verifyZeroInteractions(flatshareRepository);
		verifyZeroInteractions(roomOfferRepository);
	}

	@Test
	public void testUpdateRoomOfferForValidArguments() {
		// trigger method
		try {
			residentFacade.updateRoomOfferForFlatshare(mockedRoomOffer, mockedFlatshare);
		} catch (RoomOfferUpdateException e) {
			fail("Exception thrown for valid arguments");
		}

		// verify
		verify(flatshareRepository).update(mockedFlatshare);
		verifyNoMoreInteractions(flatshareRepository);
		verify(roomOfferRepository).update(mockedRoomOffer);
		verifyNoMoreInteractions(roomOfferRepository);
	}
}
