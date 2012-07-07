package facade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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

public class SeekerFacadeTest extends UnitTest {

	private SeekerFacade facadeUnderTest;
	private NotificationService notificationService;

	@Before
	public void setUp() {
		facadeUnderTest = new SeekerFacade();
		notificationService = mock(NotificationService.class);
		facadeUnderTest.setNotificationService(notificationService);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveRoomOfferApplicationWithInvalidOfferState() {
		// set up mocks
		Flatshare flatshare = FlatshareBuilder.validFlatshare().build();
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.DELETED;
		mockedOffer.flatshare = flatshare;
		mockedOffer.roomDetails = new RoomDetails();
		mockedOffer.roomDetails.roomSize = new FloorSpace(13);
		mockedOffer.save();
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = mockedOffer;
		mockedApplication.save();

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication.id);

		// verify
		verifyZeroInteractions(notificationService);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveRoomOfferApplicationWithInvalidApplicationState() {
		// set up mocks
		Flatshare flatshare = FlatshareBuilder.validFlatshare().build();
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		mockedOffer.flatshare = flatshare;
		mockedOffer.roomDetails = new RoomDetails();
		mockedOffer.roomDetails.roomSize = new FloorSpace(13);
		mockedOffer.save();
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = mockedOffer;
		mockedApplication.currentState = RoomOfferApplication.State.ACCEPTED;
		mockedApplication.save();

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication.id);

		// verify
		verifyZeroInteractions(notificationService);
	}

	@Test
	public void testRemoveRoomOfferApplicationWithApplicantInvited() {
		// set up mocks
		Flatshare flatshare = FlatshareBuilder.validFlatshare().build();
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		mockedOffer.flatshare = flatshare;
		mockedOffer.roomDetails = new RoomDetails();
		mockedOffer.roomDetails.roomSize = new FloorSpace(13);
		mockedOffer.save();
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = mockedOffer;
		mockedApplication.currentState = RoomOfferApplication.State.INVITED;
		mockedApplication.save();

		Long longMockedApplicationId = mockedApplication.id;

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication.id);

		// verify that notification has been sent
		verify(notificationService).notifyFlatshareOfRemovedApplication(mockedOffer, mockedApplication);

		// verify that application has been deleted#
		assertNull(RoomOfferApplication.findById(longMockedApplicationId));
	}

	@Test
	public void testRemoveRoomOfferApplicationWithApplicantNotInvited() {
		// set up mocks
		Flatshare flatshare = FlatshareBuilder.validFlatshare().build();
		RoomOffer mockedOffer = new RoomOffer();
		mockedOffer.currentState = RoomOffer.State.PUBLIC;
		mockedOffer.flatshare = flatshare;
		mockedOffer.roomDetails = new RoomDetails();
		mockedOffer.roomDetails.roomSize = new FloorSpace(13);
		mockedOffer.save();
		RoomOfferApplication mockedApplication = new RoomOfferApplication();
		mockedApplication.roomOffer = mockedOffer;
		mockedApplication.currentState = RoomOfferApplication.State.WAITING_FOR_INVITATION;
		mockedApplication.save();

		Long longMockedApplicationId = mockedApplication.id;

		// trigger method
		facadeUnderTest.removeRoomOfferApplication(mockedApplication.id);

		// verify that notification has been sent
		verifyZeroInteractions(notificationService);

		// verify that application has been deleted#
		assertNull(RoomOfferApplication.findById(longMockedApplicationId));
	}
}