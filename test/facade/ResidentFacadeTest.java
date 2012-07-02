package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import models.application.RoomOfferApplication;
import models.common.FloorSpace;
import models.flatshare.Flatshare;
import models.notification.NotificationService;
import models.offer.RoomDetails;
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
	private NotificationService notificationService;

	@Before
	public void setUp() {
		residentFacade = new ResidentFacade();
		flatshare = FlatshareBuilder.validFlatshare().build();
		roomOffer = RoomOfferBuilder.inFlatshare(flatshare).build();
		notificationService = mock(NotificationService.class);
		residentFacade.setNotificationService(notificationService);
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
		RoomOffer offer = createRoomOfferWithState(RoomOffer.State.DELETED);
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = roomOffer;
		mockedApplication.save();

		residentFacade.inviteApplicant(mockedApplication.id);
	}

	@Test(expected = IllegalStateException.class)
	public void testInviteApplicantForInvalidApplicationState() {
		// set up mocks
		RoomOffer roomOffer = createRoomOfferWithState(RoomOffer.State.PUBLIC);
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = roomOffer;
		mockedApplication.currentState = RoomOfferApplication.State.REJECTED;
		mockedApplication.save();

		residentFacade.inviteApplicant(mockedApplication.id);
	}

	@Test
	public void testInviteApplicantForValidParameters() {
		// persist room offer application for testing
		RoomOffer roomOffer = createRoomOfferWithState(RoomOffer.State.PUBLIC);
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = roomOffer;
		mockedApplication.currentState = RoomOfferApplication.State.WAITING_FOR_INVITATION;
		mockedApplication.save();

		residentFacade.inviteApplicant(mockedApplication.id);

		// verify
		verify(notificationService).notifyUserOfInvitation(mockedApplication.applicant, mockedApplication);

	}

	private RoomOffer createRoomOfferWithState(RoomOffer.State state) {
		RoomOffer offer = new RoomOffer();

		// create flatshare for offer
		Flatshare flatshare = FlatshareBuilder.validFlatshare().build();
		offer.flatshare = flatshare;
		offer.currentState = state;
		offer.flatshare = flatshare;
		offer.roomDetails = new RoomDetails();
		offer.roomDetails.roomSize = new FloorSpace(13);
		offer.save();
		return offer;
	}

}
