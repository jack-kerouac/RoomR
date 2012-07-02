package facade;

import models.application.RoomOfferApplication;
import models.flatshare.Flatshare;
import models.offer.RoomOffer;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import support.FlatshareBuilder;
import support.RoomOfferBuilder;
import facade.exception.RoomOfferUpdateException;

public class ResidentFacadeTest extends UnitTest {

	private ResidentFacade residentFacade;
	private RoomOffer roomOffer;
	private Flatshare flatshare;

	@Before
	public void setUp() {
		residentFacade = new ResidentFacade();
		flatshare = FlatshareBuilder.validFlatshare().build();
		roomOffer = RoomOfferBuilder.inFlatshare(flatshare).build();
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
		otherFlatshare.id = flatshare.id + 1;

		// trigger method
		residentFacade.updateRoomOfferForFlatshare(roomOffer, otherFlatshare);
	}

	@Test
	public void testUpdateRoomOfferForValidArguments() {
		// trigger method

		roomOffer.contactEmail = "newEmail@test.de";
		try {
			residentFacade.updateRoomOfferForFlatshare(roomOffer, flatshare);
		} catch (RoomOfferUpdateException e) {
			fail("Exception thrown for valid arguments");
		}

		// verify
		RoomOffer roomOfferFromDB = RoomOffer.findById(roomOffer.id);
		assertEquals("newEmail@test.de", roomOfferFromDB.contactEmail);
	}

	@Test(expected = IllegalStateException.class)
	public void testInviteApplicantForInvalidRoomOfferState() {
		// set up mocks
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		RoomOffer mockedRoomOffer = new RoomOffer();
		mockedRoomOffer.currentState = RoomOffer.State.DELETED;
		mockedApplication.roomOffer = mockedRoomOffer;

		// trigger method
		// TODO (Gernot) finish test
	}

	@Test(expected = IllegalStateException.class)
	public void testInviteApplicantForInvalidApplicationState() {
		// set up mocks
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.currentState = RoomOfferApplication.State.REJECTED;
		RoomOffer mockedRoomOffer = new RoomOffer();
		mockedRoomOffer.currentState = RoomOffer.State.PUBLIC;
		mockedApplication.roomOffer = mockedRoomOffer;

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
