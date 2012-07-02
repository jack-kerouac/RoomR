package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import models.application.RoomOfferApplication;
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

	@Test(expected = IllegalStateException.class)
	public void testInviteApplicantForInvalidRoomOfferState() {
		// set up mocks
		RoomOfferApplication mockedApplication = mock(RoomOfferApplication.class);
		RoomOffer mockedRoomOffer = new RoomOffer();
		mockedRoomOffer.currentState = RoomOffer.State.DELETED;
		when(mockedApplication.getRoomOffer()).thenReturn(mockedRoomOffer);

		// trigger method
		// TODO (Gernot) finish test
	}

	@Test(expected = IllegalStateException.class)
	public void testInviteApplicantForInvalidApplicationState() {
		// set up mocks
		RoomOfferApplication mockedApplication = mock(RoomOfferApplication.class);
		mockedApplication.currentState = RoomOfferApplication.State.REJECTED;
		RoomOffer mockedRoomOffer = new RoomOffer();
		mockedRoomOffer.currentState = RoomOffer.State.PUBLIC;
		when(mockedApplication.getRoomOffer()).thenReturn(mockedRoomOffer);

		// trigger method
		// TODO (Gernot) finish test
	}

	@Test
	public void testInviteApplicantForValidParameters() {
		// set up mocks
		/*
		 * final RoomOfferApplication mockedApplication =
		 * mock(RoomOfferApplication.class);
		 * when(mockedApplication.currentState)
		 * .thenReturn(RoomOfferApplication.State.WAITING_FOR_INVITATION); final
		 * RoomOffer mockedRoomOffer = new RoomOffer();
		 * mockedRoomOffer.currentState = RoomOffer.State.PUBLIC; final
		 * RoomrUser mockedApplicant = new RoomrUser();
		 * mockedApplicant.gaeUserEmail = "mockedApplicantEmail";
		 * 
		 * when(mockedApplication.getRoomOffer()).thenReturn(mockedRoomOffer);
		 * when(mockedApplication.getApplicant()).thenReturn(mockedApplicant);
		 * 
		 * // set up verification for mocked dependencies
		 * this.residentFacade.setNotificationService(new NotificationService()
		 * {
		 * 
		 * @Override public void notifyUserOfInvitation(RoomrUser applicant,
		 * RoomOfferApplication application) { assertEquals(mockedApplicant,
		 * applicant); assertEquals(mockedApplication, application); }
		 * 
		 * @Override public void notifyFlatshareOfRemovedApplication(RoomOffer
		 * offer, RoomOfferApplication application) { fail(); }
		 * 
		 * @Override public void notifyFlatshareOfNewApplication(RoomOffer
		 * offer, RoomOfferApplication application) { fail(); }
		 * 
		 * @Override public void notifyFlatshareOfCreatedOffer(RoomOffer offer)
		 * { fail(); } });
		 * 
		 * this.residentFacade.setRoomOfferApplicationRepository(new
		 * RoomOfferApplicationRepository() {
		 * 
		 * @Override public void update(RoomOfferApplication application) {
		 * assertEquals(mockedApplication, application);
		 * assertEquals(RoomOfferApplication.State.INVITED,
		 * application.currentState); }
		 * 
		 * @Override public void remove(RoomOfferApplication application) {
		 * fail(); }
		 * 
		 * @Override public Set<RoomOfferApplication> findAll() { fail(); return
		 * null; }
		 * 
		 * @Override public void add(RoomOfferApplication newApplication) {
		 * fail(); } });
		 * 
		 * // trigger method
		 * this.residentFacade.inviteApplicant(mockedApplication); } // //
		 * notify user //
		 * this.notificationService.notifyUserOfInvitation(application
		 * .getApplicant(), // application); // // // advance ROA state //
		 * application.currentState = RoomOfferApplication.State.INVITED; //
		 * this.roomOfferApplicationRepository.update(application); // }
		 */
	}
}
